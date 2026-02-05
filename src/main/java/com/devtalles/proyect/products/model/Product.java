package com.devtalles.proyect.products.model;


import com.devtalles.proyect.category.model.Category;
import com.devtalles.proyect.products.model.enums.ProductCategory;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Product {
    private Long id;
    private String name;
    private double price;
    private int stock;
    private Category category;
}
