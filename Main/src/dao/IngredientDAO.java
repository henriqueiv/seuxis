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

/**
 *
 * @author valcanaia
 */
public class IngredientDAO implements IGenericDAO<Ingredient> {

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
    public void save(Ingredient ingredient) {
        try {
            connect();

            String sql;
            sql = "INSERT INTO INGREDIENTS(ID, NAME, PRICE, AVAILABLE) VALUES (" + ingredient.getId() + ", '" + ingredient.getName() + "', " + ingredient.getPrice() + ", " + (ingredient.isAvailable() ? "1" : "0") + ");";
            System.out.println(sql);

            stmt.executeUpdate(sql);

            close();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(Ingredient ingredient) {
        try {
            connect();

            String sql = "DELETE FROM INGREDIENTS WHERE ID = " + ingredient.getId() + ";";
            System.out.println(sql);

            stmt.executeUpdate(sql);

            close();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Ingredient getById(Integer id) {
        if (id == null) {
            return null;
        }

        Ingredient ingredient = new Ingredient();

        try {
            connect();
            String sql = "SELECT ID, NAME FROM INGREDIENTS WHERE ID = " + id + ";";
            System.out.println(sql);

            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                ingredient.setId(rs.getInt("id"));
                ingredient.setName(rs.getString("name"));

                close();
                return ingredient;
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

            String sql = "SELECT MAX(ID) FROM INGREDIENTS";
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
