package com.devtalles.proyect.products.view;

import com.devtalles.proyect.products.controller.ProductController;
import com.devtalles.proyect.products.exceptions.ProductException;
import com.devtalles.proyect.products.model.enums.ProductCategory;

import java.util.Scanner;


public class ProductView {
    private final ProductController productController;
    private final Scanner sc;

    public ProductView(ProductController productController){
        this.productController = productController;
        this.sc = new Scanner(System.in);
    }

    public void showView() throws ProductException{
        boolean endApp = false;

        do{
            System.out.println("Welcome to product management system!!");
            System.out.println("Choose an option");
            System.out.println("1. Add new Product.");
            System.out.println("2. Get product by id.");
            System.out.println("3. Get all products.");
            System.out.println("4. Get all products group by category.");
            System.out.println("5. Filter products by category.");
            System.out.println("6. Filter products by price (greater than).");
            System.out.println("7. Exit.");
            int op = sc.nextInt();

            switch(op){
                case 1:
                    try{
                        saveProductView();
                    } catch (ProductException e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case 2:
                    try{
                        getProductByIdView();
                    } catch (ProductException e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case 3:
                    try {
                        getAllProductsView();
                    } catch (ProductException e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case 4:
                    try{
                        getAllProductByCategoryView();
                    } catch (ProductException e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case 5:
                    try{
                        filterByCategoryView();
                    } catch (ProductException e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case 6:
                    try{
                        filterByPriceView();
                    } catch (ProductException e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case 7:
                    endApp = true;
                    sc.close();
                    break;
                default:
                    throw new ProductException("Choose a valid option");
            }

        } while (!endApp);
    }

    private void saveProductView()throws ProductException{
        System.out.println("Id: ");
        Long id = sc.nextLong();
        sc.nextLine();

        System.out.println("Name: ");
        String name = sc.nextLine();

        System.out.println("Price: ");
        double price = sc.nextDouble();

        System.out.println("Stock");
        int stock = sc.nextInt();

        sc.nextLine();

        String category = "";

        do{
            System.out.println("Category: ");
            category = sc.nextLine();
            if(!isValid(category.trim())){
                System.out.println("Category has a not valid value");
            }
        } while(!isValid(category.trim()));


        productController.saveController(id, name, price, stock, ProductCategory.valueOf(category.toUpperCase()));
    }

    private void getProductByIdView()throws ProductException{
        sc.nextLine();

        System.out.println("Id: ");
        Long id = sc.nextLong();
        productController.getProductByIdController(id);
    }

    private void getAllProductsView()throws ProductException{
        productController.getAllProductsController();
    }

    private void getAllProductByCategoryView()throws ProductException{
        System.out.println(productController.getAllGroupByCategoryController());
    }

    private void filterByCategoryView()throws ProductException{
        sc.nextLine();

        String category = "";

        do{
            System.out.println("Category: ");
            category = sc.nextLine();
            if(!isValid(category)){
                System.out.println("Category has a not valid value");
            }
        } while(!isValid(category.trim()));

        productController.filterByCategoryController(ProductCategory.valueOf(category.toUpperCase()));
    }

    private void filterByPriceView()throws ProductException{
        sc.nextLine();

        System.out.println("Id: ");
        double price = sc.nextDouble();

        productController.filterByPriceController(price);
    }

    private boolean isValid(String value) {
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
