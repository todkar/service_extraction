package com.praful.coreproduct.services;

import com.praful.coreproduct.dtos.CoreProduct;
import com.praful.coreproduct.repositories.CoreProductRepository;

import java.util.List;

public class CoreProductService {

    private final CoreProductRepository coreProductRepository;

    public CoreProductService(CoreProductRepository coreProductRepository) {
        this.coreProductRepository = coreProductRepository;
    }

    public List<CoreProduct> getActiveProductsFor(String category) {
        return coreProductRepository.getActiveProductsFor(category);
    }
}