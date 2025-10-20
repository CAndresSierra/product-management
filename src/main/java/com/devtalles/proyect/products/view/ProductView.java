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

    public void showView(){
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
                        saveProductView();
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
                    System.out.println("Choose a valid option");
            }

        } while (!endApp);
    }

    private void saveProductView(){
        sc.nextLine();
        Long id = Long.parseLong(validateEmptyInput("Id is empty", "Id:"));
        String name = validateEmptyInput("Name is empty", "Name:");
        Double price = Double.parseDouble(validateEmptyInput("Price is empty", "Price:"));
        Integer stock = Integer.parseInt(validateEmptyInput("Stock is empty", "Stock:"));
        String category = validateEmptyInput("Category is empty", "Category:");

        try{
            productController.saveController(id, name, price, stock, category);
        } catch (ProductException e){
            System.out.println(e.getMessage());
        }


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
            if(category.trim().isEmpty()){
                System.out.println("Category has an empty value");
            }
        } while(category.trim().isEmpty());

        productController.filterByCategoryController(ProductCategory.valueOf(category.toUpperCase()));
    }

    private void filterByPriceView()throws ProductException{
        sc.nextLine();

        System.out.println("Id: ");
        double price = sc.nextDouble();

        productController.filterByPriceController(price);
    }

    private String validateEmptyInput(String message, String name){
        String input = "";
        do{
            System.out.println(name);
            input = sc.nextLine();
            if(input.trim().isEmpty()){
                System.out.println(message);
            }
        } while(input.trim().isEmpty());

        return input;

    }
}
