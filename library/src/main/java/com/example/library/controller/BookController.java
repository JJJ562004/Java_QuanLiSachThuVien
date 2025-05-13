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

import com.example.library.export.BookPdfExporter;
import com.example.library.model.Book;
import com.example.library.service.BookService;
import com.itextpdf.text.DocumentException;

import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService service;

    // 1. Danh sách hoặc tìm kiếm sách
    @GetMapping
    public String listBooks(@RequestParam(name = "keyword", required = false) String keyword,
            Model model) {
        List<Book> books = (keyword == null || keyword.isBlank()) ? service.getAllBooks()
                : service.searchBooks(keyword);

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

    // 5. Cập nhật Sách
    @PostMapping("/edit/{id}")
    public String editBook(@PathVariable String id,
            @ModelAttribute Book book) {
        Book existing_book = service.getBookById(id);
        if (existing_book == null) {
            return "redirect:/books";
        }
        // Cập nhật những trường cho phép
        existing_book.setTitle(book.getTitle());
        existing_book.setAuthor(book.getAuthor());
        existing_book.setCategory(book.getCategory());
        existing_book.setYear(book.getYear());

        service.saveBook(existing_book);
        return "redirect:/books";
    }

    // 6. Xóa sách
    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable String id) {
        service.deleteBook(id);
        return "redirect:/books";
    }

    @GetMapping("/export/pdf")
    public void exportBooksToPdf(HttpServletResponse response) throws DocumentException, IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=books.pdf");

        List<Book> books = service.getAllBooks();
        BookPdfExporter exporter = new BookPdfExporter(books);
        exporter.export(response);
    }

}
