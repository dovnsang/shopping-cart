package com.sangvndo.shoppingcart.controller;

import com.sangvndo.shoppingcart.model.Cart;
import com.sangvndo.shoppingcart.model.CartItem;
import com.sangvndo.shoppingcart.model.Product;
import com.sangvndo.shoppingcart.model.User;
import com.sangvndo.shoppingcart.repository.CartItemRepository;
import com.sangvndo.shoppingcart.repository.CartRepository;
import com.sangvndo.shoppingcart.repository.ProductRepository;
import com.sangvndo.shoppingcart.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;

@Controller
@RequiredArgsConstructor
public class CartController {
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    @GetMapping("/cart")
    public String cart(@ModelAttribute("msg") String msg,
                       Model model) {
        model.addAttribute("msg", msg);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(authentication.getName());
        Cart cart = user.getCart();
        if (cart.getCartItems() != null) {
            model.addAttribute("total", cart.getCartItems().stream().mapToDouble(item -> item.getProduct().getPrice()).sum());
        }
        model.addAttribute("cartItems", user.getCart().getCartItems());
        return "cart";
    }

    @PostMapping("/cart/items/add")
    public String addToCart(@RequestParam("id") int id,
                            @RequestParam("quantity") int quantity,
                            RedirectAttributes redirectAttributes) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(authentication.getName());
        Cart cart = user.getCart();
        if (cart.getCartItems() == null) {
            cart.setCartItems(new ArrayList<>());
        }
        CartItem cartItem = cartItemRepository.findByProductId(id);
        if (cart.getCartItems().contains(cartItem)) {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        } else {
            cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(productRepository.findById(id).get());
            cartItem.setQuantity(quantity);
            cart.getCartItems().add(cartItem);
        }
        cartRepository.save(cart);
        redirectAttributes.addFlashAttribute("msg", "Add successfully.");
        return "redirect:/products/" + id;
    }

    @PostMapping("/cart/items/{id}/remove")
    public String removeItem(@PathVariable("id") long id,
                             RedirectAttributes redirectAttributes) {
        cartItemRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("msg", "removed successfully.");
        return "redirect:/cart";
    }
}
