package com.svngdo.shoppingcart.dto;

import com.svngdo.shoppingcart.model.Category;
import lombok.Data;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
public class ProductDTO {
    private int id;
    private String name;
    private int categoryId;
    private int quantity;
    private double price;
    private double weight;
    private String description;
    private String imageName;
}
