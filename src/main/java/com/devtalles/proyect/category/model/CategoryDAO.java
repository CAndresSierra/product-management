package com.devtalles.proyect.category.model;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class CategoryDAO {
    private final Connection connection;

    public CategoryDAO(Connection connection){
        this.connection = connection;
    }

    public Category save(Category category){
        String sql = "INSERT INTO categories (name) " +
                "VALUES (?) RETURNING id";

        try(
                PreparedStatement statement = connection.prepareStatement(sql);
        ){
            statement.setString(1, category.getName());

            try(ResultSet resultSet = statement.executeQuery()){
                if(resultSet.next()){
                    long id = resultSet.getLong("id");
                    category.setId(id);
                    System.out.println("Category inserted correctly...");
                }
            }

        } catch (SQLException e){
            System.out.println(e.getMessage());
        }

        return category;
    }

    public Optional<Category> findById(Long id){
        String sql = "SELECT * FROM categories WHERE id = ?";
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

    public List<Category> findAll() throws SQLException{
        String sql = "SELECT * FROM categories";
        List<Category> categories = new ArrayList<>();
        try(
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery();
        ) {
            while (resultSet.next()) {
                Category category = this.mapResult(resultSet);
                categories.add(category);
            }

        }

        return categories;
    }

    public void update(Category category) throws SQLException{
        String sql = "UPDATE categories SET name = ? WHERE id = ?";

        try(
                PreparedStatement statement = connection.prepareStatement(sql);
        ){
            statement.setString(1, category.getName());
            statement.setDouble(2, category.getId());

            int rows = statement.executeUpdate();
            this.showMessage(rows, "Category updated correctly", "Category not found");
        }
    }

    public void delete(Long id) throws SQLException{
        String sql = "DELETE FROM categories WHERE id = ?";
        try(
                PreparedStatement statement = connection.prepareStatement(sql);
        ){
            statement.setLong(1, id);

            int rows = statement.executeUpdate();
            this.showMessage(rows, "Category deleted correctly", "Category not found");

        }
    }

    private void showMessage(int rows, String msgOk, String msgError){
        if(rows > 0){
            System.out.println(msgOk);
        } else if(!msgError.isBlank()){
            System.out.println(msgError);
        }

    }

    private Category mapResult(ResultSet resultSet) throws SQLException {
        return new Category(
                resultSet.getLong("id"),
                resultSet.getString("name")
        );
    }

}

