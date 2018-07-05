package com.praful.productprice;

import com.praful.coreproduct.repositories.CoreProductRepository;
import com.praful.coreproduct.services.CoreProductService;
import com.praful.productprice.dtos.PriceRange;
import com.praful.productprice.repositories.ProductPriceRepository;
import com.praful.productprice.services.ProductPriceService;
import org.h2.tools.DeleteDbFiles;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static junit.framework.TestCase.assertEquals;


public class ProductPriceServiceTest {
    private static String dbFolder = "./tmp/";
    private static String dbFile = "catalog";
    private static String connectionString = "jdbc:h2:" + dbFolder + dbFile;
    private static Connection connection;

    private CoreProductService coreProductService;
    private CoreProductRepository coreProductRepository;
    private ProductPriceService productPriceService;
    private ProductPriceRepository productPriceRepository;

    @BeforeClass
    public static void setup() throws Exception {
        Class.forName("org.h2.Driver");
        connection = DriverManager.getConnection(connectionString);

        try(Statement stat = connection.createStatement()) {
            stat.execute("create table core_product(id int primary key, " +
                    "name varchar(255), " +
                    "category varchar(255), " +
                    "sku varchar(255), " +
                    "is_active boolean)");

            stat.execute("create table product_price(id int primary key, " +
                    "sku varchar(255), " +
                    "original_price decimal," +
                    "sale_price decimal," +
                    "is_on_sale boolean)");

            stat.execute("insert into core_product values(1, 'Tommy Bahama Floral Shirt', 'Mens Shirts', 'SKU456', false)");

            stat.execute("insert into core_product values(2, 'BOSS Tuxedo Shirt', 'Mens Shirts', 'SKU789', true)");

            stat.execute("insert into core_product values(3, 'Banana Republic Dress Shirt', 'Mens Shirts', 'SKU123', true)");

            stat.execute("insert into core_product values(4, 'Gap Casual Shirt', 'Mens Shirts', 'SKU999', true)");


            stat.execute("insert into product_price values(1, 'SKU456', '20.99', '10.99', true)");

            stat.execute("insert into product_price values(2, 'SKU789', '240', '180', true)");

            stat.execute("insert into product_price values(3, 'SKU123', '100', '70', false)");

            stat.execute("insert into product_price values(4, 'SKU999', '150', '120', false)");
        }
    }

    @Before
    public void testSetup(){
        coreProductRepository = new CoreProductRepository(connection);
        coreProductService = new CoreProductService(coreProductRepository);
        productPriceRepository = new ProductPriceRepository(connection);

        productPriceService = new ProductPriceService(coreProductService, productPriceRepository);
    }

    @Test
    public void shouldGetSalePriceForProductOnSale() throws SQLException {
        BigDecimal actualPrice = productPriceService.getPrice("SKU789");
        assertEquals(BigDecimal.valueOf(180), actualPrice);
    }

    @Test
    public void shouldGetOriginalPriceForProductNotOnSale() throws SQLException {
        BigDecimal actualPrice = productPriceService.getPrice("SKU123");
        assertEquals(BigDecimal.valueOf(100), actualPrice);
    }

    @Test
    public void shouldGetPriceRangeForCategory() throws SQLException {
        PriceRange priceRange = productPriceService.getPriceRangeFor("Mens Shirts");
        assertEquals(BigDecimal.valueOf(100), priceRange.getMinPrice());
        assertEquals(BigDecimal.valueOf(180), priceRange.getMaxPrice());
    }

    @Test
    public void shouldUpdateIsOnSaleFlag() throws SQLException {
        assertEquals(false, productPriceRepository.getPriceFor("SKU999").isOnSale());

        productPriceService.updateIsOnSale("SKU999");

        assertEquals(true, productPriceRepository.getPriceFor("SKU999").isOnSale());
    }

    @AfterClass
    public static void tearDown() throws SQLException {
        connection.close();
        // delete the database
        DeleteDbFiles.execute(dbFolder, dbFile, true);
    }
}