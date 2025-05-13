package com.devemg;

import com.devemg.data.JDBC.ProductJDBC;
import com.devemg.data.MysqlConnection;
import com.devemg.data.dao.ProductDAO;
import com.devemg.data.entities.Product;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class HandlerProducts {
    private final ProductDAO productJDBC;

    public HandlerProducts() {
        this.productJDBC = new ProductJDBC();
    }

    public int showMenu(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("-----------------------------------------------------");
        System.out.println("\t\t PRODUCTS LIST ");
        System.out.println("------------------------------------------------------\n");
        System.out.println("1. Register new product");
        System.out.println("2. See all products");
        System.out.println("3. See single product");
        System.out.println("4. Update product");
        System.out.println("5. Delete product");
        System.out.println("6. Config database");
        System.out.println("7. Exit");
        return scanner.nextInt();
    }

    public void createProduct(){
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("Let's create a product!");
            System.out.println("Give me all the data and I'll create the product.");
            System.out.println("Name: ");
            String name = scanner.nextLine();
            System.out.println("Price: ");
            double price = scanner.nextDouble();
            System.out.println("Quantity: ");
            int qty = scanner.nextInt();
            scanner.nextLine();
            System.out.println("Description: ");
            String desc = scanner.nextLine();

            int insert = this.productJDBC.insert(new Product(name,price,qty,desc));
            if(insert > 0) {
                System.out.println("Product created!");
            }else {
                System.out.println("oh no! product can't be created");
            }
            scanner.nextLine();
        }catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }catch(InputMismatchException ex) {
            System.out.println("oh no! the input was type wrong.\n come back and try again.");
            scanner.nextLine();
            scanner.nextLine();
        }
    }

    public void showAll() {
        try {
            System.out.println("Let's list all products!");
            List<Product> products = this.productJDBC.select();
            products.forEach(System.out::println);
            new Scanner(System.in).nextLine();
        }
        catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public void showOne() {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("Let's see a product!");
            System.out.println("Give me an id of any product and I'll show it");
            int id = scanner.nextInt();
            Product product = productJDBC.select(id);
            scanner.nextLine(); //consume \n
            if(product != null) {
                System.out.println(product);
            }else {
                System.out.println("Product not found");
            }
            scanner.nextLine();
        }catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }catch (InputMismatchException ex) {
            System.out.println("oh no! the input was type wrong.\n come back and try again.");
            scanner.nextLine();
            scanner.nextLine();
        }
    }

    public void update() {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("Let's update a product!");
            System.out.println("Give me all the data to update the product.");
            System.out.println("Id of product: ");
            int id = scanner.nextInt();
            scanner.nextLine(); //consume \n
            Product product = productJDBC.select(id);
            if(product != null) {
                System.out.print("Name["+product.getName()+"]:");
                String name = scanner.nextLine();
                System.out.print("Price["+product.getprice()+"]:");
                String price = scanner.nextLine();
                System.out.print("Quantity["+product.getQuantity()+"]:");
                String qty = scanner.nextLine();
                System.out.print("Description["+product.getDescription()+"]:");
                String desc = scanner.nextLine();
                if(!name.equals("")){
                    product.setName(name);
                }
                if(!desc.equals("")){
                    product.setDescription(desc);
                }
                if(tryParseDouble(price)){
                    product.setprice(Double.parseDouble(price));
                }
                if(tryParseInt(qty)){
                    product.setQuantity(Integer.parseInt(qty));
                }
                int result = productJDBC.update(product);
                if(result > 0) {
                    System.out.println("product updated!");
                }else {
                    System.out.println("product not updated");
                }
            }else {
                System.out.println("Product not found");
            }
            scanner.nextLine();
        }catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }catch (InputMismatchException ex) {
            System.out.println("oh no! the input was type wrong.\n come back and try again.");
            scanner.nextLine();
            scanner.nextLine();
        }
    }

    public void delete() {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("Let's delete a product!");
            System.out.println("Give me an id of any product and I'll delete it");
            int id = scanner.nextInt();
            int result = productJDBC.delete(id);
            scanner.nextLine(); //consume \n
            if(result > 0) {
                System.out.println("Product deleted!");
            }else {
                System.out.println("Product not found");
            }
            scanner.nextLine();
        }catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }catch (InputMismatchException ex) {
            System.out.println("oh no! the input was type wrong.\n come back and try again.");
            scanner.nextLine();
            scanner.nextLine();
        }
    }

    public void updateDatabase() {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("Let's update the database config!");
            System.out.println("Give me all the data to update database connection.");
            System.out.print("Host["+ MysqlConnection.getHost() +"]:");
            String host = scanner.nextLine();
            System.out.print("Port["+MysqlConnection.getPort()+"]:");
            String port = scanner.nextLine();
            System.out.print("Database["+ MysqlConnection.getDatabase() +"]:");
            String database = scanner.nextLine();
            System.out.print("User["+MysqlConnection.getUser()+"]:");
            String user = scanner.nextLine();
            System.out.print("Password:");
            String password = scanner.nextLine();
            // update info
            if(!host.equals("")){
                MysqlConnection.setHost(host);
            }
            if(tryParseInt(port)){
               MysqlConnection.setPort(Integer.parseInt(port));
            }
            if(!database.equals("")){
                MysqlConnection.setDatabase(database);
            }
            if(!user.equals("")){
                MysqlConnection.setDatabaseUser(user);
            }
            //password can be empty
            MysqlConnection.setDatabasePassword(password);
            //reload connection with database
            MysqlConnection.reloadStringConnection();
            // show changes
            //System.out.println(MysqlConnection.getConnectionString());
            //System.out.println(MysqlConnection.getUser());
            //System.out.println(MysqlConnection.getPassword());
            System.out.println("database information was updated!");
            scanner.nextLine();
        }catch (InputMismatchException ex) {
            System.out.println("oh no! the input was type wrong.\n come back and try again.");
            scanner.nextLine();
            scanner.nextLine();
        }
    }

    public boolean tryParseInt(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean tryParseDouble(String value) {
        try {
            Double.parseDouble(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
