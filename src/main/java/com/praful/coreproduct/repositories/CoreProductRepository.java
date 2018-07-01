package com.praful.coreproduct.repositories;

import com.praful.coreproduct.dtos.CoreProduct;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CoreProductRepository {

    private Connection connection;

    public CoreProductRepository(Connection connection){
        this.connection = connection;
    }

    public CoreProduct getProduct(String sku) throws SQLException {
        CoreProduct coreProduct;

        String sql = "select * from core_product where sku = ?";
        try(PreparedStatement stat = connection.prepareStatement(sql)) {
            stat.setString(1, sku);
            ResultSet rs = stat.executeQuery();
            rs.next();

            coreProduct = new CoreProduct(rs.getString("name"),
                    rs.getString("category"),
                    rs.getString("sku"),
                    rs.getBoolean("is_active"));
        }

        return coreProduct;
    }

    public List<CoreProduct> getActiveProductsFor(String category) throws SQLException {
        List<CoreProduct> products = new ArrayList<>();

        String sql = "select * from core_product where category = ? and is_active = true";
        try (PreparedStatement stat = connection.prepareStatement(sql)){
            stat.setString(1, category);
            ResultSet rs = stat.executeQuery();

            while(rs.next()) {
                CoreProduct coreProduct = new CoreProduct(rs.getString("name"),
                        rs.getString("category"),
                        rs.getString("sku"),
                        rs.getBoolean("is_active"));

                products.add(coreProduct);
            }
        }

        return products;
    }

    public void save(CoreProduct coreProduct) throws SQLException {
        String sql = "update core_product set name = ?, category = ?, sku = ?, is_active = ? where sku = ?";

        try(PreparedStatement stat = connection.prepareStatement(sql)) {
            stat.setString(1, coreProduct.getName());
            stat.setString(2, coreProduct.getCategory());
            stat.setString(3, coreProduct.getSku());
            stat.setBoolean(4, coreProduct.isActive());
            stat.setString(5, coreProduct.getSku());

            stat.executeUpdate();
        }
    }
}
