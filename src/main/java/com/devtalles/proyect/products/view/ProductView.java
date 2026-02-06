package com.devtalles.proyect.products.view;

import com.devtalles.proyect.category.model.Category;
import com.devtalles.proyect.category.model.CategoryDAO;
import com.devtalles.proyect.products.controller.ProductController;
import com.devtalles.proyect.products.exceptions.ProductException;
import com.devtalles.proyect.products.model.Product;
import com.devtalles.proyect.products.persistence.ProductDAO;

import java.sql.SQLException;
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
                        getProductByIdView();
                    break;
                case 3:
                        getAllProductsView();
                    break;
                case 4:
                        getAllProductByCategoryView();
                    break;
                case 5:
                        filterByCategoryView();
                    break;
                case 6:
                        filterByPriceView();
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
        String name = validateEmptyInput("Name is empty", "Name:");
        double price = Double.parseDouble(validateEmptyInput("Price is empty", "Price:"));
        int stock = Integer.parseInt(validateEmptyInput("Stock is empty", "Stock:"));
        String category = validateEmptyInput("Category is empty", "Category:");

        try{
            Category newCategory = new Category(category.toUpperCase());
            productController.saveController(new Product(name, price, stock, newCategory));
        } catch (ProductException | SQLException e){
            System.out.println(e.getMessage());
        }
    }

    private void getProductByIdView(){
        sc.nextLine();

        Long id = Long.parseLong(validateEmptyInput("Id is empty", "Id:"));
        try{
            productController.getProductByIdController(id);
        } catch (ProductException e){
            System.out.println(e.getMessage());
        }
    }

    private void getAllProductsView(){
        try{
        productController.getAllProductsController();
        }catch (ProductException e){
            System.out.println(e.getMessage());
        }
    }

    private void getAllProductByCategoryView(){
        try {
            System.out.println(productController.getAllGroupByCategoryController());
        } catch (ProductException e){
            System.out.println(e.getMessage());
        }
    }

    private void filterByCategoryView(){
        try {
            sc.nextLine();

            String category = validateEmptyInput("Category is empty", "Category:");

            productController.filterByCategoryController(category);
        }catch (ProductException e){
            System.out.println(e.getMessage());
        }
    }

    private void filterByPriceView(){

        try{
            sc.nextLine();
            double price = Double.parseDouble(validateEmptyInput("Prices is empty", "Price:"));

            productController.filterByPriceController(price);
        } catch (ProductException e){
            System.out.println(e.getMessage());
        }

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
