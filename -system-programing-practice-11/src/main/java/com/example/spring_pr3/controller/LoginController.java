package com.example.spring_pr3.controller; // або інший твій actual package

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model; // (опціонально, якщо будеш передавати повідомлення в шаблон)

@Controller
public class LoginController {

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
}
