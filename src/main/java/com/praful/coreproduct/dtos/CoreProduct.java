package com.praful.coreproduct.dtos;

public class CoreProduct {

    private final String name;
    private final String category;
    private final String sku;
    private final boolean isActive;

    public CoreProduct(String name, String category, String sku, boolean isActive) {
        this.name = name;
        this.category = category;
        this.sku = sku;
        this.isActive = isActive;
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

    public boolean isActive() {
        return isActive;
    }
}
