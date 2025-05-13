package com.example.library.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.library.model.Reader;
import com.example.library.service.ReaderService;

@Controller
public class LoginController {

    @Autowired
    private ReaderService readerService;

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String name, @RequestParam String email, Model model) {
        if ("admin".equals(name) && "123@gmail.com".equals(email)) {
            return "index-admin";
        }

        Optional<Reader> reader = readerService.findByNameAndEmail(name, email);
        if (reader.isPresent()) {
            model.addAttribute("reader", reader.get());
            return "index";
        }

        model.addAttribute("error", "Sai tên hoặc email!");
        return "login";
    }
}
