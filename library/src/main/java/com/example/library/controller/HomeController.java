package com.example.library.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
  @GetMapping("/index-admin")
  public String home_admin(){
    return "index-admin";
  }
}