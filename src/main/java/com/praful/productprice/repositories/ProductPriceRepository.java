package com.praful.productprice.repositories;

import com.praful.productprice.dtos.ProductPrice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductPriceRepository {
    private Connection connection;

    public ProductPriceRepository(Connection connection){
        this.connection = connection;
    }

    public ProductPrice getPriceFor(String sku) throws SQLException {
        ProductPrice productPrice;

        String sql = "select * from product_price where sku = ?";
        try(PreparedStatement stat = connection.prepareStatement(sql)) {
            stat.setString(1, sku);
            ResultSet rs = stat.executeQuery();
            rs.next();

            productPrice = new ProductPrice(rs.getString("sku"),
                    rs.getBigDecimal("original_price"),
                    rs.getBigDecimal("sale_price"),
                    rs.getBoolean("is_on_sale"));
        }

        return productPrice;
    }

    public List<ProductPrice> getProductPricesFor(List<String> productSkus) throws SQLException {
        List<ProductPrice> productPrices = new ArrayList<>();

        String sql = "select * from product_price where sku = any (?)";
        try (PreparedStatement stat = connection.prepareStatement(sql)){

            final String[] values = productSkus.toArray(new String[0]);
            stat.setArray(1, connection.createArrayOf("varchar(255)", values));
            ResultSet rs = stat.executeQuery();

            while(rs.next()) {
                ProductPrice productPrice = new ProductPrice(rs.getString("sku"),
                        rs.getBigDecimal("original_price"),
                        rs.getBigDecimal("sale_price"),
                        rs.getBoolean("is_on_sale"));

                productPrices.add(productPrice);
            }
        }
        return productPrices;
    }

    public void save(ProductPrice productPrice) throws SQLException {
        String sql = "update product_price set sku = ?, original_price = ?, sale_price = ?, is_on_sale = ? where sku = ?";

        try(PreparedStatement stat = connection.prepareStatement(sql)) {
            stat.setString(1, productPrice.getSku());
            stat.setBigDecimal(2, productPrice.getOriginalPrice());
            stat.setBigDecimal(3, productPrice.getSalePrice());
            stat.setBoolean(4, productPrice.isOnSale());
            stat.setString(5, productPrice.getSku());

            stat.executeUpdate();
        }
    }

}
