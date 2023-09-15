package com.sangvndo.shoppingcart.controller;

import com.sangvndo.shoppingcart.repository.UserRepository;
import com.sangvndo.shoppingcart.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final UserRepository userRepository;

    @GetMapping("/")
    public String home(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(authentication.getName());
        if (user != null) {
            model.addAttribute("firstName", user.getFirstName());
            return "home";
        }
        return "redirect:/products";
    }

}
