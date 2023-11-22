/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import models.Products;

/**
 *
 * @author argen
 */
public class CrudDB {

    PreparedStatement ps = null;
    ConexionDB connection = new ConexionDB();
    ResultSet rs = null;

    public void createProduct(String name, String description, int price, String url){
        try {
            Statement st = connection.getConexion().createStatement();
            String query = "INSERT INTO products (name,description,price,url) values (?,?,?,?)";
            ps = connection.getConexion().prepareStatement(query);
            ps.setString(1, name);
            ps.setString(2, description);
            ps.setInt(3, price);
            ps.setString(4, url);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public ArrayList<Products> receiveAllProducts(){
        ArrayList<Products> products = new ArrayList<>();
        try{
            String query = "SELECT * FROM products";
            ps = connection.getConexion().prepareStatement(query);
            rs = ps.executeQuery();
            while(rs.next()){
                Products product = new Products(
                        rs.getString("name"),
                        rs.getString("description"),
                        Integer.parseInt(rs.getString("price")),
                        rs.getString("url")
                );
                products.add(product);
            }
            
        } catch (Exception e){
            System.out.println(e);
        }
        return products;
    }

    public Products receiveProduct(int id) {
        try {
            String query = "SELECT * FROM products WHERE id = ?";
            ps = connection.getConexion().prepareStatement(query);
            ps.setString(1, String.valueOf(id));
            rs = ps.executeQuery();
            if (rs.next()) {
                Products product = new Products(
                        rs.getString("name"),
                        rs.getString("description"),
                        Integer.parseInt(rs.getString("price")),
                        rs.getString("url")
                );
                return product;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }
}
