package com.devtalles.proyect.category.model;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class CategoryDAO {
    public Optional<Category> save(Connection connection, Category category){
        String sql = "INSERT INTO categories (name) " +
                "VALUES (?)";

        try(
                PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        ){
            statement.setString(1, category.getName());

            int rows = statement.executeUpdate();
            if(rows > 0){
            try(ResultSet generatedKey = statement.getGeneratedKeys()) {
                if (generatedKey.next()) {
                    category.setId(generatedKey.getLong(1));
                    return Optional.of(category);
                }
            }
                System.out.println("Category insert correctly");
            }

        } catch (SQLException e){
            System.out.println(e.getMessage());
        }

        return Optional.empty();
    }

    public Optional<Category> findById(Connection connection, Long id){
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

    public Optional<Category> findByName(Connection connection, String name){
        String sql = "SELECT * FROM categories WHERE name = ?";
        try(
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1, name);

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

    public List<Category> findAll(Connection connection) throws SQLException{
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

    public void update(Connection connection, Category category) throws SQLException{
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

    public void delete(Connection connection, Long id) throws SQLException{
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

