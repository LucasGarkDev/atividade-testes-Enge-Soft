package com.devemg.data.dao;
import java.sql.Connection;
import java.sql.SQLException;

import com.devemg.data.JDBC.ProductJDBC;
import com.devemg.data.MysqlConnection;
import com.devemg.data.entities.Product;

public class TestTransactional {
    public static void main(String[] args) {
        Connection con = null;
        try{
            ProductDAO productHandler = new ProductJDBC(con);
            con = MysqlConnection.getConnection();
            con.setAutoCommit(false);
            Product product = new Product("Product A",2,2,"Description");
            Product product2 = new Product("Product A",2,2,"Description");
            productHandler.insert(product);
            productHandler.delete(100);
            productHandler.insert(product2);
            con.commit();
        }catch (SQLException ex) {
            try {
                con.rollback();
            } catch (SQLException throwables) {
                System.err.println(throwables.getMessage());
            }
            System.err.println(ex.getMessage());
        }
    }
}
