package com.praful.catalog.repositories;

import com.praful.catalog.dtos.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository {

    private Connection connection;

    public ProductRepository(Connection connection){
        this.connection = connection;

    }

    public Product getProduct(String sku) throws SQLException {
        String sql = "select * from catalog where sku = ?";
        PreparedStatement stat = connection.prepareStatement(sql);
        stat.setString(1, sku);
        ResultSet rs = stat.executeQuery();
        rs.next();

        Product product = new Product(rs.getString("name"),
                rs.getString("category"),
                rs.getString("sku"),
                rs.getBoolean("is_active"),
                rs.getBigDecimal("original_price"),
                rs.getBigDecimal("sale_price"),
                rs.getBoolean("is_on_sale"));

        stat.close();

        return product;
    }

    public List<Product> findProductsFor(String category) throws SQLException {
        String sql = "select * from catalog where category = ?";
        PreparedStatement stat = connection.prepareStatement(sql);
        stat.setString(1, category);
        ResultSet rs = stat.executeQuery();

        List<Product> products = new ArrayList<>();
        while(rs.next()) {
            Product product = new Product(rs.getString("name"),
                    rs.getString("category"),
                    rs.getString("sku"),
                    rs.getBoolean("is_active"),
                    rs.getBigDecimal("original_price"),
                    rs.getBigDecimal("sale_price"),
                    rs.getBoolean("is_on_sale"));

            products.add(product);
        }

        return products;
    }

    public void save(Product product) throws SQLException {
        String sql = "update catalog set name = ?, category = ?, sku = ?, is_active = ?, original_price = ?, sale_price = ?, is_on_sale = ? where sku = ?";
        PreparedStatement stat = connection.prepareStatement(sql);
        stat.setString(1, product.getName());
        stat.setString(2, product.getCategory());
        stat.setString(3, product.getSku());
        stat.setBoolean(4, product.isActive());
        stat.setBigDecimal(5, product.getOriginalPrice());
        stat.setBigDecimal(6, product.getSalePrice());
        stat.setBoolean(7, product.isOnSale());
        stat.setString(8, product.getSku());

        stat.executeUpdate();
    }
}
