package com.example.library.controller;

import com.example.library.model.Book;
import com.example.library.service.BookService;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService service;

    // 1. Danh sách hoặc tìm kiếm sách
    @GetMapping
    public String listBooks(@RequestParam(name = "keyword", required = false) String keyword,
                            Model model) {
        List<Book> books = (keyword == null || keyword.isBlank()) ?
            service.getAllBooks() :
            service.searchBooks(keyword);

        model.addAttribute("books", books);
        model.addAttribute("keyword", keyword);
        return "books";
    }

    // 2. Form thêm sách
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("book", new Book());
        return "add-book";
    }

    // 3. Lưu sách mới
    @PostMapping("/add")
    public String addBook(@ModelAttribute Book book) {
        service.saveBook(book);
        return "redirect:/books";
    }

    // 4. Form sửa sách
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable String id, Model model) {
        model.addAttribute("book", service.getBookById(id));
        return "edit-book";
    }

    // 5. Cập nhật sách
    @PostMapping("/edit/{id}")
    public String editBook(@PathVariable String id, @ModelAttribute Book book) {
        book.setId(id);
        service.saveBook(book);
        return "redirect:/books";
    }

    // 6. Xóa sách
    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable String id) {
        service.deleteBook(id);
        return "redirect:/books";
    }
    @GetMapping("/readers/export/pdf")
public void exportToPDF(HttpServletResponse response) throws IOException, DocumentException {
    response.setContentType("application/pdf");
    response.setHeader("Content-Disposition", "attachment; filename=readers.pdf");

    List<Reader> readers = readerService.getAllReaders();

    ReaderPdfExporter exporter = new ReaderPdfExporter(readers);
    exporter.export(response);
}

}
