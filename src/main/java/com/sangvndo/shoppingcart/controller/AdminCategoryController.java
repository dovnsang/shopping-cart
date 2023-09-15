package com.sangvndo.shoppingcart.controller;

import com.sangvndo.shoppingcart.model.Category;
import com.sangvndo.shoppingcart.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class AdminCategoryController {
    private final CategoryRepository categoryRepository;

    @GetMapping("/admin/categories")
    public String viewCategories(Model model,
                                 @ModelAttribute("msg") String msg) {
        model.addAttribute("msg", msg);
        model.addAttribute("categories", categoryRepository.findAll());
        return "adminCategories";
    }

    @GetMapping("/admin/categories/add")
    public String addCategoryForm(Model model) {
        model.addAttribute("category", new Category());
        return "categoryAdd";
    }

    @PostMapping("/admin/categories/add")
    public String saveCategory(RedirectAttributes redirectAttributes,
                               @ModelAttribute("category") Category category) {
        category = categoryRepository.save(category);
        String msg;
        if (categoryRepository.findById(category.getId()).isPresent()) {
            msg = "Add successfully!";
        } else {
            msg = "Add failed";
        }
        redirectAttributes.addFlashAttribute("msg", msg);
        return "redirect:/admin/categories/add";
    }

    @GetMapping("/admin/categories/{id}/update")
    public String updateCategoryForm(@PathVariable("id") int id,
                                     Model model) {
        model.addAttribute("category", categoryRepository.findById(id));
        return "categoryUpdate";
    }

    @PostMapping("/admin/categories/update")
    public String updateCategory(@ModelAttribute("category") Category category,
                                 RedirectAttributes redirectAttributes) {
        category = categoryRepository.save(category);
        String msg;
        if (categoryRepository.findById(category.getId()).isPresent()) {
            msg = "Update successfully!";
        } else {
            msg = "Update failed";
        }
        redirectAttributes.addFlashAttribute("msg", msg);
        return "redirect:/admin/categories/" + category.getId() + "/update";
    }

    @GetMapping("/admin/categories/{id}/delete")
    public String deleteCategory(@PathVariable("id") int id) {
        categoryRepository.deleteById(id);
        return "redirect:/admin/categories";
    }
}
