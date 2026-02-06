package com.devtalles.proyect.products.model;


import com.devtalles.proyect.category.model.Category;
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

    public Product(String name, double price, int stock, Category category) {
        this.stock = stock;
        this.name = name;
        this.price = price;
        this.category = category;
    }
}
