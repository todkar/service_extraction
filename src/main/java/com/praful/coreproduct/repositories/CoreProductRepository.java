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

    public CoreProductRepository(Connection connection) {
        this.connection = connection;
    }

    public List<CoreProduct> getActiveProductsFor(String category) {
        List<CoreProduct> products = new ArrayList<>();

        String sql = "select * from core_product where category = ? and is_active = true";
        try {
            try (PreparedStatement stat = connection.prepareStatement(sql)) {

                stat.setString(1, category);
                ResultSet rs = stat.executeQuery();

                while (rs.next()) {
                    CoreProduct coreProduct = new CoreProduct(rs.getString("name"),
                            rs.getString("category"),
                            rs.getString("sku"),
                            rs.getBoolean("is_active"));

                    products.add(coreProduct);
                }
            }
        } catch (SQLException sqlException) {
            throw new RuntimeException(sqlException);
        }

        return products;
    }

}
