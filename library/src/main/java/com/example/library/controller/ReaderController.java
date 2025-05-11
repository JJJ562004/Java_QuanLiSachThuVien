package com.example.library.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.library.model.Reader;
import com.example.library.service.ReaderService;
import com.itextpdf.text.DocumentException;

import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/readers")
public class ReaderController {

    @Autowired
    private ReaderService service;

    // 1. Danh sách hoặc tìm kiếm bạn đọc
    @GetMapping
    public String listReaders(@RequestParam(name = "keyword", required = false) String keyword,
                            Model model) {
        List<Reader> readers = (keyword == null || keyword.isBlank()) ?
            service.getAllReaders() :
            service.searchReaders(keyword);

        model.addAttribute("readers", readers);
        model.addAttribute("keyword", keyword);
        return "readers";
    }

    // 2. Form thêm bạn đọc
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("reader", new Reader());
        return "add-reader";
    }

    // 3. Lưu bạn đọc mới
    @PostMapping("/add")
    public String addReader(@ModelAttribute Reader reader) {
        service.saveReader(reader);
        return "redirect:/readers";
    }

    // 4. Form sửa bạn đọc
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable String id, Model model) {
        model.addAttribute("reader", service.getReaderById(id));
        return "edit-reader";
    }

    // 5. Cập nhật bạn đọc
    @PostMapping("/edit/{id}")
    public String editReader(@PathVariable String id, @ModelAttribute Reader reader) {
        reader.setId(id);
        service.saveReader(reader);
        return "redirect:/readers";
    }

    // 6. Xóa bạn đọc
    @GetMapping("/delete/{id}")
    public String deleteReader(@PathVariable String id) {
        service.deleteReader(id);
        return "redirect:/readers";
    }
    @GetMapping("/readers/export/pdf")
public void exportToPDF(HttpServletResponse response) throws IOException, DocumentException {
    response.setContentType("application/pdf");
    response.setHeader("Content-Disposition", "attachment; filename=readers.pdf");

    List<Reader> readers = service.getAllReaders();

    ReaderPdfExporter exporter = new ReaderPdfExporter(readers);
    exporter.export(response);
}

}
