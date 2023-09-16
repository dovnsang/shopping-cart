package com.sangvndo.shoppingcart.controller;

import com.sangvndo.shoppingcart.model.Cart;
import com.sangvndo.shoppingcart.model.User;
import com.sangvndo.shoppingcart.repository.CartRepository;
import com.sangvndo.shoppingcart.repository.RoleRepository;
import com.sangvndo.shoppingcart.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;

@Controller
@RequiredArgsConstructor
public class AuthController {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final CartRepository cartRepository;

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @GetMapping("/register")
    public String registerForm(@ModelAttribute("msg") String msg, Model model) {
        model.addAttribute("msg", msg);
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("user") User user,
                           RedirectAttributes redirectAttributes) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            redirectAttributes.addFlashAttribute("msg", "User existed");
            return "redirect:/register";
        }
        user.setRoles(Collections.singletonList(roleRepository.findById(2).get()));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCart(new Cart());
        userRepository.save(user);
        redirectAttributes.addFlashAttribute("msg", "Register successfully.");
        return "redirect:/register";
    }
}
