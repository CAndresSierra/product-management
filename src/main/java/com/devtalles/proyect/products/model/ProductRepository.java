package com.devtalles.proyect.products.model;

import com.devtalles.proyect.products.exceptions.ProductException;
import com.devtalles.proyect.products.model.enums.ProductCategory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class ProductRepository {
    private final List<Product> products;

    public ProductRepository(){
        this.products = new ArrayList<>();
    }

    public void save(Long id, String name, double price, int stock, String category) {

        try{
           ProductCategory categoryF =  ProductCategory.valueOf(category.toUpperCase());
            Product newProduct = new Product(id, name, price, stock, categoryF);

            products.add(newProduct);
            System.out.println("Product has been added successfully");
        } catch (IllegalArgumentException e){
            System.out.println("Category has a non-valid value");
        }

    }

    public Product getProductById(Long id) throws ProductException {
        if(products.isEmpty()){
            throw new ProductException("Product list is empty.");
        }
       Optional<Product> productFound = products.stream()
               .filter(p -> p.getId() == id)
               .findFirst();

        if(productFound.isPresent()){
            return productFound.get();
        } else {
            throw new ProductException("Product with id: " + id + " dont exist.");
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
                                p -> p.getCategory().name()
                        ));
    }

    public void filterByCategory(ProductCategory category) throws ProductException {
        if(products.isEmpty()){
            throw new ProductException("Product list is empty.");
        }

        Optional<List<Product>> productsFound = Optional.of(products.stream()
                .filter(p -> p.getCategory().name().equalsIgnoreCase(category.name()))
                .toList());

        productsFound.ifPresent( products -> products.forEach(p -> {
            System.out.println("-----------------------------");
            System.out.println("Id: " + p.getId());
            System.out.println("Name: " + p.getName());
            System.out.println("Price: " + p.getPrice());
            System.out.println("Stock: " + p.getStock());
            System.out.println("Category: " + p.getCategory().name());
            System.out.println("-----------------------------");
            System.out.println(" ");
        }));
        productsFound.orElseThrow(() -> new ProductException("There arent products"));

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
            System.out.println("Category: " + p.getCategory().name());
            System.out.println("-----------------------------");
            System.out.println(" ");
        }) );
        productsFound.orElseThrow(() -> new ProductException("There arent products with a price more than " + "$" + price));
    }


}
