package com.example.library.controller;

import com.example.library.model.Reader;
import com.example.library.service.ReaderService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
}
