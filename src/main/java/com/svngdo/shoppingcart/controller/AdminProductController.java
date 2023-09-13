package com.svngdo.shoppingcart.controller;

import com.svngdo.shoppingcart.dto.ProductDTO;
import com.svngdo.shoppingcart.model.Product;
import com.svngdo.shoppingcart.repository.CategoryRepository;
import com.svngdo.shoppingcart.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequiredArgsConstructor
public class AdminProductController {
    public static String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/images/products";

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @GetMapping("/admin/products")
    public String getProducts(Model model,
                              @ModelAttribute("msg") String msg) {
        model.addAttribute("msg", msg);
        model.addAttribute("products", productRepository.findAll());
        return "products";
    }

    @GetMapping("/admin/products/add")
    public String getAddProductForm(Model model) {
        model.addAttribute("productDTO", new ProductDTO());
        model.addAttribute("categories", categoryRepository.findAll());
        return "productAdd";
    }

    @PostMapping("/admin/products/add")
    public String addProduct(@ModelAttribute("productDTO") ProductDTO productDTO,
                             @RequestParam("imageFile") MultipartFile imageFile,
                             @RequestParam("imageName") String imageName,
                             RedirectAttributes redirectAttributes) throws IOException {
        if (!imageFile.isEmpty()) {
            imageName = imageFile.getOriginalFilename();
            Path path = Paths.get(uploadDir, imageName);
            Files.write(path, imageFile.getBytes());
        }

        Product product = new Product();
        product.setName(productDTO.getName());
        product.setCategory(categoryRepository.findById(productDTO.getCategoryId()).get());
        product.setQuantity(productDTO.getQuantity());
        product.setPrice(productDTO.getPrice());
        product.setWeight(productDTO.getWeight());
        product.setDescription(productDTO.getDescription());
        product.setImageName(imageName);
        product = productRepository.save(product);
        String msg;
        if (productRepository.existsById(product.getId())) {
            msg = "Add product successfully.";
        } else {
            msg = "Add product failed.";
        }
        redirectAttributes.addFlashAttribute("msg", msg);
        return "redirect:/admin/products/add";
    }

    @GetMapping("/admin/products/{id}/update")
    public String getUpdateProductForm(@PathVariable("id") int id,
                                       Model model) {
        Product p = productRepository.findById(id).get();
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(p.getId());
        productDTO.setName(p.getName());
        productDTO.setCategoryId(p.getCategory().getId());
        productDTO.setQuantity(p.getQuantity());
        productDTO.setPrice(p.getPrice());
        productDTO.setWeight(p.getWeight());
        productDTO.setDescription(p.getDescription());
        productDTO.setImageName(p.getImageName());

        model.addAttribute("productDTO", productDTO);
        model.addAttribute("categories", categoryRepository.findAll());
        return "productUpdate";
    }

    @PostMapping("/admin/products/update")
    public String updateProduct(@ModelAttribute("productDTO") ProductDTO productDTO,
                                @RequestParam("imageFile") MultipartFile imageFile,
                                @RequestParam("imageName") String imageName,
                                RedirectAttributes redirectAttributes) throws IOException {
        Product product = productRepository.findById(productDTO.getId()).get();
        if (!productRepository.existsById(product.getId())) {
            return "404";
        }
        if (!imageFile.isEmpty()) {
            imageName = imageFile.getOriginalFilename();
            Path path = Paths.get(uploadDir, imageName);
            Files.write(path, imageFile.getBytes());
        }
        product.setName(productDTO.getName());
        product.setCategory(categoryRepository.findById(productDTO.getCategoryId()).get());
        product.setQuantity(productDTO.getQuantity());
        product.setPrice(productDTO.getPrice());
        product.setWeight(productDTO.getWeight());
        product.setDescription(productDTO.getDescription());
        product.setImageName(imageName);
        productRepository.save(product);

        String msg = "Update product successfully.";
        redirectAttributes.addFlashAttribute("msg", msg);
        return "redirect:/admin/products/add";
    }

    @GetMapping("/admin/products/{id}/delete")
    public String deleteProduct(@PathVariable("id") int id,
                                RedirectAttributes redirectAttributes) {
        productRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("msg", "Delete successfully.");
        return "redirect:/admin/products";
    }
}
