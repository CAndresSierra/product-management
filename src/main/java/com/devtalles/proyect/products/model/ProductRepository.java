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

    public void save(Long id, String name, double price, int stock, ProductCategory category) throws ProductException {
        if(!isValid(category.name())){
            throw new ProductException("Category has a not valid value: " + category.name());
        }

        Product newProduct = new Product(id, name, price, stock, category);

        products.add(newProduct);
        System.out.println("Product has been added successfully");
    }

    public void getProductById(Long id) throws ProductException {
        if(products.isEmpty()){
            throw new ProductException("Product list is empty.");
        }
       Optional<Product> productFound = products.stream()
               .filter(p -> p.getId() == id)
               .findFirst();

        if(productFound.isPresent()){
            productFound.stream()
                    .forEach(p -> {
                        System.out.println("-----------------------------");
                        System.out.println("Id: " + p.getId());
                        System.out.println("Name: " + p.getName());
                        System.out.println("Price: " + p.getPrice());
                        System.out.println("Stock: " + p.getStock());
                        System.out.println("Category: " + p.getCategory().name());
                        System.out.println("-----------------------------");
                        System.out.println(" ");
                    });
        } else {
            throw new ProductException("Product with id: " + id + " dont exist.");
        }

    }

    public void getAllProducts()throws ProductException {
        if(this.products.isEmpty()){
            throw new ProductException("Product list is empty.");
        }

        this.products
                .forEach( product ->{
                        System.out.println("-----------------------------");
                        System.out.println("Id: " + product.getId());
                        System.out.println("Name: " + product.getName());
                        System.out.println("Price: " + product.getPrice());
                        System.out.println("Stock: " + product.getStock());
                        System.out.println("Category: " + product.getCategory().name());
                        System.out.println("-----------------------------");
                        System.out.println(" ");
                });

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

        if(!isValid(category.name())){
            throw new ProductException("Category has a not valid value: " + category.name());
        }

        List<Product> productsFound = products.stream()
                .filter( p -> p.getCategory().name().equalsIgnoreCase(category.name()))
                .toList();

        Optional<List<Product>> optionalProductsFound = Optional.of(productsFound);

        if(optionalProductsFound.isPresent()){
            productsFound
                    .forEach( p  -> {
                        System.out.println("-----------------------------");
                        System.out.println("Id: " + p.getId());
                        System.out.println("Name: " + p.getName());
                        System.out.println("Price: " + p.getPrice());
                        System.out.println("Stock: " + p.getStock());
                        System.out.println("Category: " + p.getCategory().name());
                        System.out.println("-----------------------------");
                        System.out.println(" ");
                    });
        } else {
            throw new ProductException("There arent products");
        }




    }

    public void filterByPrice(Long price) throws ProductException {
        if(products.isEmpty()){
            throw new ProductException("Product list is empty.");
        }

        List<Product> productsFound = products.stream()
                .filter( p -> p.getPrice() > price)
                .toList();

        Optional<List<Product>> optionalProductsFound = Optional.of(productsFound);
        if(optionalProductsFound.isPresent()){
            productsFound
                    .forEach( p  -> {
                        System.out.println("-----------------------------");
                        System.out.println("Id: " + p.getId());
                        System.out.println("Name: " + p.getName());
                        System.out.println("Price: " + p.getPrice());
                        System.out.println("Stock: " + p.getStock());
                        System.out.println("Category: " + p.getCategory().name());
                        System.out.println("-----------------------------");
                        System.out.println(" ");
                    });
        } else {
            throw new ProductException("There arent products with a price more than " + "$" + price);
        }
    }

    public boolean isValid(String value) {
        if (value == null || value.isEmpty()) {
            return false;
        }
        try {
            ProductCategory.valueOf(value.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }


}
