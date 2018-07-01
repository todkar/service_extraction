package com.praful.catalog;

import com.praful.catalog.dtos.PriceRange;
import com.praful.catalog.repositories.ProductRepository;
import com.praful.catalog.services.CatalogService;
import org.h2.tools.DeleteDbFiles;
import org.junit.AfterClass;
import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.Before;


import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static junit.framework.TestCase.assertEquals;


public class CatalogServiceTest {

    private static Connection connection;
    private CatalogService catalogService;
    private ProductRepository productRepository;

    @BeforeClass
    public static void setup() throws Exception {
        Class.forName("org.h2.Driver");
        connection = DriverManager.getConnection("jdbc:h2:/Users/tw/thoughtworks/coding/service_extraction/catalog");
        try(Statement stat = connection.createStatement()) {
            stat.execute("create table catalog(id int primary key, " +
                    "name varchar(255), " +
                    "category varchar(255), " +
                    "sku varchar(255), " +
                    "is_active boolean, " +
                    "original_price decimal," +
                    "sale_price decimal," +
                    "is_on_sale boolean)");
            stat.execute("insert into catalog values(1, 'Tommy Bahama Floral Shirt', 'Mens Shirts', 'SKU456', false, '20.99', '10.99', true)");

            stat.execute("insert into catalog values(2, 'BOSS Tuxedo Shirt', 'Mens Shirts', 'SKU789', true, '240', '180', true)");

            stat.execute("insert into catalog values(3, 'Banana Republic Dress Shirt', 'Mens Shirts', 'SKU123', true, '100', '70', false)");

            stat.execute("insert into catalog values(4, 'Gap Casual Shirt', 'Mens Shirts', 'SKU999', true, '150', '120', false)");
        }
    }

    @Before
    public void testSetup(){
        productRepository = new ProductRepository(connection);
        catalogService = new CatalogService(productRepository);
    }

    @Test
    public void shouldGetSalePriceForProductOnSale() throws SQLException {
        BigDecimal actualPrice = catalogService.getPrice("SKU789");
        assertEquals(BigDecimal.valueOf(180), actualPrice);
    }

    @Test
    public void shouldGetOriginalPriceForProductNotOnSale() throws SQLException {
        BigDecimal actualPrice = catalogService.getPrice("SKU123");
        assertEquals(BigDecimal.valueOf(100), actualPrice);
    }

    @Test
    public void shouldGetPriceRangeForCategory() throws SQLException {
        PriceRange priceRange = catalogService.getPriceRangeFor("Mens Shirts");
        assertEquals(BigDecimal.valueOf(100), priceRange.getMinPrice());
        assertEquals(BigDecimal.valueOf(180), priceRange.getMaxPrice());
    }

    @Test
    public void shouldUpdateIsOnSaleFlag() throws SQLException {
        assertEquals(false, productRepository.getProduct("SKU999").isOnSale());

        catalogService.updateIsOnSale("SKU999");

        assertEquals(true, productRepository.getProduct("SKU999").isOnSale());
    }

    @AfterClass
    public static void tearDown() throws SQLException {
        connection.close();
        // delete the database
        DeleteDbFiles.execute("/Users/tw/thoughtworks/coding/service_extraction", "catalog", true);
    }
}