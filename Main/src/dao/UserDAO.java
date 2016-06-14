package dao;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.User;

public class UserDAO implements IGenericDAO<User> {

    Connection c = null;
    Statement stmt = null;

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
    public void save(User user) {

        try {
            connect();

            String sql = "INSERT INTO USERS(ID, CPF, NAME, EMAIL, MANAGER) VALUES (" + user.getId() + ", '" + user.getCpf() + "', '"
                    + user.getName() + "', '" + user.getEmail() + "', '" + user.isManager() + "');";
            System.out.println(sql);

            stmt.executeUpdate(sql);

            close();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(User user) {

        try {

            connect();

            String sql = "DELETE FROM USERS WHERE ID = " + user.getId() + ";";
            System.out.println(sql);

            stmt.executeUpdate(sql);

            close();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public User getById(Integer id) {
        if (id == null) {
            return null;
        }

        User user = new User();

        try {
            connect();
            String sql = "SELECT * FROM USERS WHERE ID = " + id + ";";
            System.out.println(sql);

            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                user.setId(rs.getInt("id"));
                user.setCpf(rs.getString("cpf"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setManager(rs.getBoolean("manager"));

                System.out.println(user);

                close();
                return user;
            }

            close();
            return null;
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Integer getMaxId() {
        int id;

        try {
            connect();

            String sql = "SELECT MAX(ID) FROM USERS";
            System.out.println(sql);

            ResultSet rs = stmt.executeQuery(sql);

            id = rs.getInt(1);

            close();
            return id;
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public User getUserByCpf(String cpf) {
        User user = new User();

        try {
            connect();

            String sql = "SELECT ID, CPF, NAME, EMAIL, MANAGER FROM USERS WHERE CPF = '" + cpf + "'";
            System.out.println(sql);

            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                user.setId(rs.getInt("id"));
                user.setCpf(rs.getString("cpf"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setManager(rs.getBoolean("manager"));

                close();
                return user;
            }

            close();
            return null;
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
