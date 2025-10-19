package com.devtalles.proyect.products.model;


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
    private int category;
}
