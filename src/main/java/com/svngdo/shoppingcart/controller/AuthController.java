package com.svngdo.shoppingcart.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

//    @GetMapping("/register")
//    public String registerForm() {
//        return "register";
//    }

}
