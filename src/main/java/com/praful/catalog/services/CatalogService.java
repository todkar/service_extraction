package com.praful.catalog.services;

import com.praful.catalog.dtos.PriceRange;
import com.praful.catalog.dtos.Product;
import com.praful.catalog.repositories.ProductRepository;

import java.math.BigDecimal;
import java.util.List;

public class CatalogService {

    private final ProductRepository productRepository;

    public CatalogService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public BigDecimal getPrice(String sku) {
        Product product = productRepository.getProduct(sku);
        return calculatePrice(product);
    }

    private BigDecimal calculatePrice(Product product) {
        if(product.isOnSale()) return product.getSalePrice();
        return product.getOriginalPrice();
    }

    public PriceRange getPriceRangeFor(String category) {
        List<Product> products = productRepository.findProductsFor(category);
        BigDecimal maxPrice = null;
        BigDecimal minPrice = null;
        for (Product product : products) {
            if (product.isActive()) {
                BigDecimal productPrice = calculatePrice(product);
                if (maxPrice == null || productPrice.compareTo(maxPrice) > 0) {
                    maxPrice = productPrice;
                }
                if (minPrice == null || productPrice.compareTo(minPrice) < 0) {
                    minPrice = productPrice;
                }
            }
        }
        return new PriceRange(category, minPrice, maxPrice);
    }

    public void updateIsOnSale(String sku) {
        final Product product = productRepository.getProduct(sku);
        product.setOnSale(true);
        productRepository.save(product);
    }

}
