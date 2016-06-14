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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Ingredient;
import models.IngredientsItem;
import models.Item;

/**
 *
 * @author valcanaia
 */
public class ItemDAO implements IGenericDAO<Item> {

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
    public void save(Item item) {
        try {
            connect();
            
            List<Ingredient> ingredients = item.getIngredients();
            IngredientsItemDAO ingredientsItemDao = new IngredientsItemDAO();
            ingredients.stream().forEach((ingredient) -> {
                ingredientsItemDao.save(new IngredientsItem(ingredientsItemDao.getMaxId()+1, ingredient, item));
            });
            
            String sql = "INSERT INTO ITEMS(ID, NAME) VALUES (" + item.getId() + ", '" + item.getName() + "');";
            System.out.println(sql);
            stmt.executeUpdate(sql);

            close();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(Item item) {
        try {
            connect();

            String sql = "DELETE FROM ITEMS WHERE ID = " + item.getId() + ";";
            System.out.println(sql);

            stmt.executeUpdate(sql);

            close();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Item getById(Integer id) {
        if (id == null) {
            return null;
        }

        Item item = new Item();

        try {
            connect();
            String sql = "SELECT ID, NAME FROM ITEMS WHERE ID = " + id + ";";
            System.out.println(sql);

            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                item.setId(rs.getInt("id"));
                item.setName(rs.getString("name"));

                close();
                return item;
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

            String sql = "SELECT MAX(ID) FROM ITEMS";
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
