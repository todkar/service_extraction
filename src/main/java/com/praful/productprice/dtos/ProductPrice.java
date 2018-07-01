package com.praful.productprice.dtos;

import java.math.BigDecimal;

public class ProductPrice {

    private final String sku;
    private BigDecimal originalPrice;
    private BigDecimal salePrice;
    private boolean isOnSale;

    public ProductPrice(String sku, BigDecimal originalPrice, BigDecimal salePrice, boolean isOnSale) {
        this.sku = sku;
        this.originalPrice = originalPrice;
        this.salePrice = salePrice;
        this.isOnSale = isOnSale;
    }

    public BigDecimal getOriginalPrice() {
        return originalPrice;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public boolean isOnSale() {
        return isOnSale;
    }

    public void setOnSale(boolean isOnSale) {
        this.isOnSale = isOnSale;
    }

    public String getSku() {
        return sku;
    }
}
