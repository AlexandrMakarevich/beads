package com.beads.model.dao;

import com.beads.model.builder.ProductBuilder;
import com.beads.model.domain.Product;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ProductDaoImplTest extends CommonDaoIT {

    @Autowired
    private ProductDao productDao;
    private ProductBuilder productBuilder;

    @Test
    public void deleteProduct() {
        Product p = new Product();
        p.setId(5);
        productDao.removeProduct(p);
    }

    @Test
    public void testSearchProductBySearchString() throws Exception {
        List<Product> products = productDao.searchProductBySearchString("имя");
    }

    @Test
    public void testLoadProductsByGroupId() throws Exception {
        List<Product> products = productDao.loadProductByGroupId(2);
    }

    @Test
    public void testLoadProductById() throws Exception {
        Product pr =productDao.loadProductById(1);
    }

    @Test
    public void testSaveProduct() {
        productBuilder = new ProductBuilder();
        Product expectedProduct = productBuilder.build();
        productDao.saveOrUpdate(expectedProduct);

        Product actualProduct = productDao.loadProductById(expectedProduct.getId());

        Assert.assertEquals("Actual result must be expected",
            actualProduct, expectedProduct);
    }
}