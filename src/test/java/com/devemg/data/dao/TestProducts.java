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
        System.out.println("🔍 Executando: testeInsercaoValida");
        Product p = new Product("Café", 10.5, 20, "Café torrado");
        int resultado = dao.insert(p);
        assertEquals(1, resultado, "❌ Esperado 1, mas retornou diferente.");
    }

    @Test
    public void testeInsercaoNomeNulo() throws SQLException {
        System.out.println("🔍 Executando: testeInsercaoNomeNulo");
        Product p = new Product(null, 10.5, 20, "Sem nome");
        int resultado = dao.insert(p);
        assertEquals(0, resultado, "❌ Esperado 0, mas retornou diferente.");
    }

    @Test
    public void testeSelecaoProdutoExistente() throws SQLException {
        System.out.println("🔍 Executando: testeSelecaoProdutoExistente");
        Product p = new Product("Notebook", 2500, 5, "Dell Inspiron");
        dao.insert(p);

        List<Product> todos = dao.select();
        assertFalse(todos.isEmpty(), "❌ Esperado que a lista não fosse vazia.");

        Product buscado = dao.select(todos.get(0).getIdProduct());
        assertNotNull(buscado, "❌ Produto buscado está nulo.");
        assertEquals("Notebook", buscado.getName(), "❌ Nome não corresponde ao esperado.");
    }

    @Test
    public void testeSelecaoProdutoInexistente() throws SQLException {
        System.out.println("🔍 Executando: testeSelecaoProdutoInexistente");
        Product p = dao.select(9999);
        assertNull(p, "❌ Esperado nulo para produto inexistente.");
    }

    @Test
    public void testeAtualizacaoValida() throws SQLException {
        System.out.println("🔍 Executando: testeAtualizacaoValida");
        Product p = new Product("Teclado", 100, 10, "Mecânico");
        dao.insert(p);

        List<Product> produtos = dao.select();
        Product existente = produtos.get(0);
        existente.setprice(120);
        existente.setQuantity(15);

        int resultado = dao.update(existente);
        assertEquals(1, resultado, "❌ Atualização válida falhou.");
    }

    @Test
    public void testeAtualizacaoProdutoInexistente() throws SQLException {
        System.out.println("🔍 Executando: testeAtualizacaoProdutoInexistente");
        Product p = new Product(9999, "Fake", 1.0, 1, "Não existe");
        int resultado = dao.update(p);
        assertEquals(0, resultado, "❌ Atualizou um produto que não existe.");
    }

    @Test
    public void testeRemocaoProdutoExistente() throws SQLException {
        System.out.println("🔍 Executando: testeRemocaoProdutoExistente");
        Product p = new Product("Monitor", 900, 3, "Full HD");
        dao.insert(p);

        List<Product> produtos = dao.select();
        int id = produtos.get(0).getIdProduct();

        int resultado = dao.delete(id);
        assertEquals(1, resultado, "❌ Remoção de produto existente falhou.");
    }

    @Test
    public void testeRemocaoProdutoInexistente() throws SQLException {
        System.out.println("🔍 Executando: testeRemocaoProdutoInexistente");
        int resultado = dao.delete(9999);
        assertEquals(0, resultado, "❌ Tentou remover um produto que não existe.");
    }

    @Test
    public void testeListagemProdutos() throws SQLException {
        System.out.println("🔍 Executando: testeListagemProdutos");
        dao.insert(new Product("Produto 1", 10, 2, "Desc 1"));
        dao.insert(new Product("Produto 2", 20, 3, "Desc 2"));
        dao.insert(new Product("Produto 3", 30, 4, "Desc 3"));

        List<Product> produtos = dao.select();
        assertEquals(3, produtos.size(), "❌ Quantidade de produtos listados está incorreta.");
    }

    @Test
    public void testeToStringProduto() {
        System.out.println("🔍 Executando: testeToStringProduto");
        Product p = new Product(1, "Teste", 10.0, 5, "descrição");
        String texto = p.toString();
        assertTrue(texto.contains("idProduct:1"), "❌ idProduct não aparece na toString.");
        assertTrue(texto.contains("name:'Teste'"), "❌ Nome do produto não aparece corretamente.");
    }
}