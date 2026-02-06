package com.devtalles.proyect;


import com.devtalles.proyect.category.model.CategoryDAO;
import com.devtalles.proyect.db.ConnectionPool;
import com.devtalles.proyect.products.controller.ProductController;
import com.devtalles.proyect.products.exceptions.ProductException;
import com.devtalles.proyect.products.model.ProductRepository;
import com.devtalles.proyect.products.view.ProductView;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try(Connection connection = ConnectionPool.getConnection()){
            System.out.println("Database successfully connected");
            CategoryDAO categoryDAO = new CategoryDAO();
            ProductRepository productRepository = new ProductRepository(categoryDAO);
            ProductController productController = new ProductController(productRepository);
            ProductView view  = new ProductView(productController);
            view.showView();
        } catch (SQLException | ProductException e) {
            System.out.println("Entro al error");
            System.out.println("Message: " + e.getMessage());
        } finally {
            ConnectionPool.closePool();
        }
    }
}