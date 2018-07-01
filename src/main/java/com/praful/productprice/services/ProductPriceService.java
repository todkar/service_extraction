package com.praful.productprice.services;

import com.praful.coreproduct.dtos.CoreProduct;
import com.praful.coreproduct.services.CoreProductService;
import com.praful.productprice.dtos.PriceRange;
import com.praful.productprice.dtos.ProductPrice;
import com.praful.productprice.repositories.ProductPriceRepository;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class ProductPriceService {

    private final CoreProductService coreProductService;
    private ProductPriceRepository productPriceRepository;

    public ProductPriceService(CoreProductService coreProductService, ProductPriceRepository productPriceRepository){
        this.coreProductService = coreProductService;
        this.productPriceRepository = productPriceRepository;
    }

    public BigDecimal getPrice(String sku) throws SQLException {
        ProductPrice productPrice = productPriceRepository.getPriceFor(sku);
        return calculatePrice(productPrice);
    }

    private BigDecimal calculatePrice(ProductPrice productPrice) {
        if(productPrice.isOnSale()) return productPrice.getSalePrice();
        return productPrice.getOriginalPrice();
    }

    public PriceRange getPriceRangeFor(String category) throws SQLException {
        List<CoreProduct> products = coreProductService.getActiveProductsFor(category);

        List<ProductPrice> productPrices = productPriceRepository.getProductPricesFor(mapCoreProductToSku(products));

        BigDecimal maxPrice = null;
        BigDecimal minPrice = null;
        for (ProductPrice productPrice : productPrices) {
                BigDecimal currentProductPrice = calculatePrice(productPrice);
                if (maxPrice == null || currentProductPrice.compareTo(maxPrice) > 0) {
                    maxPrice = currentProductPrice;
                }
                if (minPrice == null || currentProductPrice.compareTo(minPrice) < 0) {
                    minPrice = currentProductPrice;
                }
        }
        return new PriceRange(category, minPrice, maxPrice);
    }

    private List<String> mapCoreProductToSku(List<CoreProduct> coreProducts) {
        return coreProducts.stream().map(p -> p.getSku()).collect(Collectors.toList());
    }

    public void updateIsOnSale(String sku) throws SQLException {
        final ProductPrice productPrice = productPriceRepository.getPriceFor(sku);
        productPrice.setOnSale(true);
        productPriceRepository.save(productPrice);
    }

}
