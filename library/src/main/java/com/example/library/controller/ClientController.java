package com.example.library.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.library.model.Book;
import com.example.library.service.BookService;

@Controller
public class ClientController {

    @Autowired
    private BookService bookService;

    @GetMapping("/index")
public String showClientBookList(@RequestParam(name = "keyword", required = false) String keyword, Model model) {
    List<Book> books;
    if (keyword != null && !keyword.isEmpty()) {
        books = bookService.searchBooks(keyword);
    } else {
        books = bookService.getAllBooks();
    }
    model.addAttribute("books", books);
    model.addAttribute("keyword", keyword);
    return "index";
}
}
