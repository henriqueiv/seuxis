/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Ingredient;
import models.IngredientsItem;
import models.Item;

/**
 *
 * @author valcanaia
 */
public class IngredientsItemDAO implements IGenericDAO<IngredientsItem> {

    private Connection c = null;
    private Statement stmt = null;

    public void connect() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        c = DriverManager.getConnection("jdbc:sqlite:src/database/seuxis.db");
        System.out.println("\n---------Opened database successfully---------");

        stmt = c.createStatement();
    }

    public void close() throws SQLException {
        stmt.close();
        c.close();
        System.out.println("---------Closed database successfully---------\n");
    }

    @Override
    public void save(IngredientsItem ingredientsItem) {
        try {
            connect();

            String sql = "INSERT INTO INGREDIENTS_ITEM(ID, ID_INGREDIENT, ID_ITEM) VALUES (" + ingredientsItem.getId() + ", " + ingredientsItem.getIngredient().getId() + ", " + ingredientsItem.getItem().getId() + ");";
            System.out.println(sql);
            stmt.executeUpdate(sql);

            close();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(IngredientsItem ingredientItem) {
        try {
            connect();

            String sql = "DELETE FROM INGREDIENTS_ITEM WHERE ID = " + ingredientItem.getId() + ";";
            System.out.println(sql);

            stmt.executeUpdate(sql);

            close();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public IngredientsItem getById(Integer id) {
        if (id == null) {
            return null;
        }

        try {
            connect();
            String sql = "SELECT ID, ID_INGREDIENT, ID_ITEM FROM INGREDIENTS_ITEM WHERE ID = " + id + ";";
            System.out.println(sql);

            IngredientsItem ingredientItem = new IngredientsItem();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                ingredientItem.setId(rs.getInt("id"));

                IngredientDAO ingredientDAO = new IngredientDAO();
                Ingredient ingredient = ingredientDAO.getById(rs.getInt("id_ingredient"));
                ingredientItem.setIngredient(ingredient);

                ItemDAO itemDAO = new ItemDAO();
                Item item = itemDAO.getById(rs.getInt("id_item"));
                ingredientItem.setItem(item);

                close();
                return ingredientItem;
            }
            close();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Integer getMaxId() {
        try {
            connect();

            String sql = "SELECT MAX(ID) FROM INGREDIENTS_ITEM";
            System.out.println(sql);

            ResultSet rs = stmt.executeQuery(sql);

            int id;
            id = rs.getInt(1);

            close();
            return id;
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
