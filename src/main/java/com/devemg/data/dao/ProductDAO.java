package com.devemg.data.dao;

import com.devemg.data.entities.Product;

import java.sql.SQLException;
import java.util.List;

public interface ProductDAO {

    public int insert(Product product) throws SQLException;
    public int update(Product product) throws SQLException;
    public int delete(int idProduct) throws SQLException;
    public Product select(int idProduct) throws SQLException;
    public List<Product> select() throws SQLException;

}
