package com.devtalles.proyect.products.persistence;

import com.devtalles.proyect.category.model.Category;
import com.devtalles.proyect.category.model.CategoryDAO;
import com.devtalles.proyect.products.model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductDAO {
    private final Connection connection;
    private final CategoryDAO categoryDAO;

    public ProductDAO(Connection connection, CategoryDAO categoryDAO) {
        this.connection = connection;
        this.categoryDAO = categoryDAO;
    }

    public Product save(Product product) throws SQLException {
        String sql = "INSERT INTO products (name, price, stock, category_id) " +
                " VALUES (?, ?, ?, ?)";

        try(
                PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ){
            statement.setString(1, product.getName());
            statement.setDouble(2, product.getPrice());
            statement.setInt(3, product.getStock());
            statement.setLong(4, product.getCategory().getId());

            int rows = statement.executeUpdate();
            if(rows > 0){
                try(ResultSet generatedKey = statement.getGeneratedKeys()){
                    if(generatedKey.next()){
                        product.setId(generatedKey.getLong(1));
                        System.out.println("Product insert correctly");
                    }
                }
            }

        }

        return product;
    }

    public void update(Product product) throws SQLException{
        String sql = "UPDATE products SET name = ?, price = ?, stock = ?, category_id = ? " +
                " WHERE id = ?";

        try(
                PreparedStatement statement = connection.prepareStatement(sql);
        ){
            statement.setString(1, product.getName());
            statement.setDouble(2, product.getPrice());
            statement.setInt(3, product.getStock());
            statement.setLong(4, product.getCategory().getId());
            statement.setLong(5, product.getId());

            int rows = statement.executeUpdate();
            this.showMessage(rows, "Product updated correctly", "Product not found");
        }
    }

    public void delete(Long id) throws SQLException{
        String sql = "DELETE FROM products WHERE id = ?";
        try(
                PreparedStatement statement = connection.prepareStatement(sql);
        ){
            statement.setLong(1, id);

            int rows = statement.executeUpdate();
            this.showMessage(rows, "Product deleted correctly", "Product not found");

        }
    }

    public List<Product> findAll() throws SQLException{
        String sql = "SELECT p.id, p.name, p.price, p.stock, p.category_id,"  +
                " c.name as category_name" +
                " FROM products p JOIN categories c ON p.category_id = c.id"
                ;
        List<Product> productsList = new ArrayList<>();
        try(
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery();
        ) {
            while (resultSet.next()) {
                Product product = this.mapResult(resultSet);
                productsList.add(product);
            }

        }

        return productsList;
    }

    public boolean existById(Long id){
        if(id == null) return false;
        return this.findById(id).isPresent();
    }

    public Optional<Product> findById(Long id){
        String sql = "SELECT p.id, p.name, p.price, p.stock, p.category_id\n" +
                " c.name as category_name\n" +
                " FROM products p JOIN categories c ON p.category_id = c.id WHERE p.id = ?"
                ;
        try(
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()){

                if(resultSet.next()){
                    return Optional.of(this.mapResult(resultSet));
                }

            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return Optional.empty();
    }

    public List<Product> findByCategoryId(Long categoryId){
        String sql = "SELECT p.id, p.name, p.price, p.stock, p.category_id\n" +
                " c.name as category_name\n" +
                " FROM products p JOIN categories c ON p.category_id = c.id WHERE p.category_id = ?"
                ;
        List<Product> products = new ArrayList<>();
        try(
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setLong(1, categoryId);

            try (ResultSet resultSet = statement.executeQuery()){

                while(resultSet.next()){
                   products.add(this.mapResult(resultSet));
                }

            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return products;
    }

    private void showMessage(int rows, String msgOk, String msgError){
        if(rows > 0){
            System.out.println(msgOk);
        } else if(!msgError.isBlank()){
            System.out.println(msgError);
        }

    }

    private Product mapResult(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("category_id");
        String cateName = resultSet.getString("category_name");
        Category category = new Category(id, cateName);
        return new Product(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getDouble("price"),
                resultSet.getInt("stock"),
                category
        );
    }

}
