package com.devtalles.proyect;


import com.devtalles.proyect.products.controller.ProductController;
import com.devtalles.proyect.products.exceptions.ProductException;
import com.devtalles.proyect.products.model.ProductRepository;
import com.devtalles.proyect.products.view.ProductView;

public class Main {
    public static void main(String[] args) {
        ProductRepository productRepository = new ProductRepository();
        ProductController productController = new ProductController(productRepository);
        ProductView view  = new ProductView(productController);

        try{
            view.showView();
        } catch (ProductException e){
            System.out.println(e.getMessage());
        }
    }
}