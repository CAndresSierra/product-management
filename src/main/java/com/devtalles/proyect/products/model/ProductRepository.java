package com.devtalles.proyect.products.model;

import com.devtalles.proyect.category.model.Category;
import com.devtalles.proyect.category.model.CategoryDAO;
import com.devtalles.proyect.db.ConnectionPool;
import com.devtalles.proyect.products.exceptions.ProductException;
import com.devtalles.proyect.products.model.enums.ProductCategory;
import com.devtalles.proyect.products.persistence.ProductDAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class ProductRepository {
    private final List<Product> products;
    private final ProductDAO productDAO;
    private final CategoryDAO categoryDAO;

    public ProductRepository(CategoryDAO categoryDAO) throws SQLException, ProductException {
        this.productDAO = new ProductDAO(categoryDAO);
        this.categoryDAO = categoryDAO;
        try(Connection connection = ConnectionPool.getConnection()){
            this.products = productDAO.findAll(connection);
        } catch (SQLException e){
            throw new ProductException("An error occurred during the product list initializing " + e.getMessage());
        }
    }

    public void save(Connection connection, Product product) throws SQLException {
        Optional<Category> optionalCategory= categoryDAO.findByName(connection, product.getCategory().getName());
        if(optionalCategory.isPresent()){
         product.setCategory(optionalCategory.get());
         productDAO.save(connection, product);
         products.add(product);
        } else {
            Optional<Category> optionalNewCategory = categoryDAO.save(connection, product.getCategory());
            product.setCategory(optionalNewCategory.get());
            productDAO.save(connection, product);
            products.add(product);
        }

    }

    public Product getProductById(Long id) throws ProductException {
        if(products.isEmpty()){
            throw new ProductException("Product list is empty.");
        }
       Optional<Product> productFound = products.stream()
               .filter(p -> Objects.equals(p.getId(), id))
               .findFirst();

        if(productFound.isPresent()){
            return productFound.get();
        } else {
            throw new ProductException("Product with id: " + id + " don't exist.");
        }

    }

    public List<Product> getAllProducts()throws ProductException {
        if(this.products.isEmpty()){
            throw new ProductException("Product list is empty.");
        }

        return products;

    }

    public Map<String, List<Product>> getAllGroupByCategory()throws ProductException{
        if(products.isEmpty()){
            throw new ProductException("Product list is empty.");
        }

        return products.stream()
                        .collect(Collectors.groupingBy(
                                p -> p.getCategory().getName()
                        ));
    }

    public void filterByCategory(String category) throws ProductException {
        if(products.isEmpty()){
            throw new ProductException("Product list is empty.");
        }

        try{
            ProductCategory categoryF = ProductCategory.valueOf(category.toUpperCase());
            Optional<List<Product>> productsFound = Optional.of(products.stream()
                    .filter(p -> p.getCategory().getName().equalsIgnoreCase(categoryF.name()))
                    .toList());

            productsFound.ifPresent( products -> products.forEach(p -> {
                System.out.println("-----------------------------");
                System.out.println("Id: " + p.getId());
                System.out.println("Name: " + p.getName());
                System.out.println("Price: " + p.getPrice());
                System.out.println("Stock: " + p.getStock());
                System.out.println("Category: " + p.getCategory().getName());
                System.out.println("-----------------------------");
                System.out.println(" ");
            }));
            productsFound.orElseThrow(() -> new ProductException("There arent products"));
        } catch (IllegalArgumentException e){
            System.out.println("Category has a non-valid value.");
        }
    }

    public void filterByPrice(double price) throws ProductException {
        if(products.isEmpty()){
            throw new ProductException("Product list is empty.");
        }

        Optional<List<Product>> productsFound = Optional.of(products.stream()
                .filter( p -> p.getPrice() > price)
                .toList());

        productsFound.ifPresent( products -> products.forEach(p -> {
            System.out.println("-----------------------------");
            System.out.println("Id: " + p.getId());
            System.out.println("Name: " + p.getName());
            System.out.println("Price: " + p.getPrice());
            System.out.println("Stock: " + p.getStock());
            System.out.println("Category: " + p.getCategory().getName());
            System.out.println("-----------------------------");
            System.out.println(" ");
        }) );
        productsFound.orElseThrow(() -> new ProductException("There arent products with a price more than " + "$" + price));
    }

    public void delete(Connection connection, Long id) throws SQLException {
        products.removeIf(product -> product.getId().equals(id));
        productDAO.delete(connection, id);
    }

}
