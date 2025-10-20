package com.devtalles.proyect.products.controller;

import com.devtalles.proyect.products.exceptions.ProductException;
import com.devtalles.proyect.products.model.Product;
import com.devtalles.proyect.products.model.ProductRepository;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Map;


@AllArgsConstructor
public class ProductController {
    private final ProductRepository productRepository;

    public void saveController(Long id, String name, double price, int stock, String category)throws ProductException {
        productRepository.save(id, name, price, stock, category);
    }

    public void getProductByIdController(Long id)throws ProductException{
        Product productFound = productRepository.getProductById(id);
        System.out.println("-----------------------------");
        System.out.println("Id: " + productFound.getId());
        System.out.println("Name: " + productFound.getName());
        System.out.println("Price: " + productFound.getPrice());
        System.out.println("Stock: " + productFound.getStock());
        System.out.println("Category: " + productFound.getCategory().name());
        System.out.println("-----------------------------");
        System.out.println(" ");
    }

    public void getAllProductsController()throws ProductException{
       List<Product> products =  productRepository.getAllProducts();
        products
                .forEach( p -> {
                    System.out.println("-----------------------------");
                    System.out.println("Id: " + p.getId());
                    System.out.println("Name: " + p.getName());
                    System.out.println("Price: " + p.getPrice());
                    System.out.println("Stock: " + p.getStock());
                    System.out.println("Category: " + p.getCategory().name());
                    System.out.println("-----------------------------");
                    System.out.println(" ");
                });
    }

    public Map<String, List<Product>> getAllGroupByCategoryController()throws ProductException{
        return productRepository.getAllGroupByCategory();
    }

    public void filterByCategoryController(String category)throws ProductException{
        productRepository.filterByCategory(category);
    }

    public void filterByPriceController(double price)throws ProductException{
        productRepository.filterByPrice(price);
    }

}
