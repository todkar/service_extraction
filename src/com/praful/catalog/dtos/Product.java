package com.praful.catalog.dtos;

import java.math.BigDecimal;

public class Product {
    private String name;
    private String category;
    private String sku;
    private BigDecimal salePrice;
    private BigDecimal originalPrice;
    private boolean isOnSale;
    private boolean isActive;

    public Product(String name, String category, String sku, boolean isActive, BigDecimal originalPrice, BigDecimal salePrice, boolean isOnSale) {
        this.name = name;
        this.category = category;
        this.sku = sku;
        this.isActive = isActive;
        this.originalPrice = originalPrice;
        this.salePrice = salePrice;
        this.isOnSale = isOnSale;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getSku() {
        return sku;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public BigDecimal getOriginalPrice() {
        return originalPrice;
    }

    public boolean isOnSale() {
        return isOnSale;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    public void setOriginalPrice(BigDecimal originalPrice) {
        this.originalPrice = originalPrice;
    }

    public void setOnSale(boolean onSale) {
        isOnSale = onSale;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
