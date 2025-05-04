package com.example.library.controller;

import com.example.library.model.Book;
import com.example.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService service;

    // 1. Danh sách sách
    @GetMapping
    public String listBooks(Model model) {
        model.addAttribute("books", service.getAllBooks());
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
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("book", service.getBookById(id));
        return "edit-book";
    }

    // 5. Cập nhật sách
    @PostMapping("/edit/{id}")
    public String editBook(@PathVariable Long id, @ModelAttribute Book book) {
        book.setId(id);
        service.saveBook(book);
        return "redirect:/books";
    }

    // 6. Xóa sách
    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        service.deleteBook(id);
        return "redirect:/books";
    }
}
