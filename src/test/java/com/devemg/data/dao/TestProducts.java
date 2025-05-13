package com.devemg.data.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import com.devemg.data.JDBC.ProductJDBC;
import com.devemg.data.MysqlConnection;
import com.devemg.data.entities.Product;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class TestProducts {

    private Connection connection;
    private ProductDAO dao;

    @BeforeEach
    public void setup() throws SQLException {
        connection = MysqlConnection.getConnection();
        dao = new ProductJDBC(connection);

        try (PreparedStatement stmt = connection.prepareStatement("DELETE FROM product")) {
            stmt.execute();
        }
    }

    @AfterEach
    public void fecharConexao() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    @Test
    public void testeInsercaoValida() throws SQLException {
        Product p = new Product("Café", 10.5, 20, "Café torrado");
        int resultado = dao.insert(p);
        assertEquals(1, resultado);
    }

    @Test
    public void testeInsercaoNomeNulo() throws SQLException {
        Product p = new Product(null, 10.5, 20, "Sem nome");
        int resultado = dao.insert(p);
        assertEquals(0, resultado); // ou verifique se lança exceção, se for esperado
    }

    @Test
    public void testeSelecaoProdutoExistente() throws SQLException {
        Product p = new Product("Notebook", 2500, 5, "Dell Inspiron");
        dao.insert(p);

        List<Product> todos = dao.select();
        assertFalse(todos.isEmpty());

        Product buscado = dao.select(todos.get(0).getIdProduct());
        assertNotNull(buscado);
        assertEquals("Notebook", buscado.getName());
    }

    @Test
    public void testeSelecaoProdutoInexistente() throws SQLException {
        Product p = dao.select(9999);
        assertNull(p);
    }

    @Test
    public void testeAtualizacaoValida() throws SQLException {
        Product p = new Product("Teclado", 100, 10, "Mecânico");
        dao.insert(p);

        List<Product> produtos = dao.select();
        Product existente = produtos.get(0);
        existente.setprice(120);
        existente.setQuantity(15);

        int resultado = dao.update(existente);
        assertEquals(1, resultado);
    }

    @Test
    public void testeAtualizacaoProdutoInexistente() throws SQLException {
        Product p = new Product(9999, "Fake", 1.0, 1, "Não existe");
        int resultado = dao.update(p);
        assertEquals(0, resultado);
    }

    @Test
    public void testeRemocaoProdutoExistente() throws SQLException {
        Product p = new Product("Monitor", 900, 3, "Full HD");
        dao.insert(p);

        List<Product> produtos = dao.select();
        int id = produtos.get(0).getIdProduct();

        int resultado = dao.delete(id);
        assertEquals(1, resultado);
    }

    @Test
    public void testeRemocaoProdutoInexistente() throws SQLException {
        int resultado = dao.delete(9999);
        assertEquals(0, resultado);
    }

    @Test
    public void testeListagemProdutos() throws SQLException {
        dao.insert(new Product("Produto 1", 10, 2, "Desc 1"));
        dao.insert(new Product("Produto 2", 20, 3, "Desc 2"));
        dao.insert(new Product("Produto 3", 30, 4, "Desc 3"));

        List<Product> produtos = dao.select();
        assertEquals(3, produtos.size());
    }

    @Test
    public void testeToStringProduto() {
        Product p = new Product(1, "Teste", 10.0, 5, "descrição");
        String texto = p.toString();
        assertTrue(texto.contains("idProduct:1"));
        assertTrue(texto.contains("name:'Teste'"));
    }
}