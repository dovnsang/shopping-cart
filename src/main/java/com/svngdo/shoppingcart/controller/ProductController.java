package com.svngdo.shoppingcart.controller;

import com.svngdo.shoppingcart.model.Category;
import com.svngdo.shoppingcart.model.Product;
import com.svngdo.shoppingcart.repository.CategoryRepository;
import com.svngdo.shoppingcart.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProductController {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @GetMapping("/products")
    public String getProducts(Model model,
                              @RequestParam(value = "categoryId", required = false) Integer categoryId) {
        List<Product> products;
        if (categoryId == null) {
            products = productRepository.findAll();
        } else {
            products = productRepository.findAllByCategoryId(categoryId);
        }
        model.addAttribute("products", products);
        model.addAttribute("categories", categoryRepository.findAll());
        return "products";
    }

    @GetMapping("/products/{id}")
    public String getProduct(@PathVariable("id") int id,
                             Model model) {
        model.addAttribute("product", productRepository.findById(id).get());
        return "product";
    }

}
