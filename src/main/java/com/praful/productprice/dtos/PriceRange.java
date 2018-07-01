package com.praful.productprice.dtos;

import java.math.BigDecimal;

public class PriceRange {
    private final String category;
    private final BigDecimal minPrice;
    private final BigDecimal maxPrice;

    public PriceRange(String category, BigDecimal minPrice, BigDecimal maxPrice) {
        this.category = category;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }

    public BigDecimal getMinPrice() {
        return minPrice;
    }

    public BigDecimal getMaxPrice() {
        return maxPrice;
    }
}
