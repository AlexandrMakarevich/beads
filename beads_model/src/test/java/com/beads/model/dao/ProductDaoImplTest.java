package com.beads.model.dao;

import static com.beads.model.builder.ProductGroupPersistentBuilder.PRODUCT_GROUP_PERSISTENT_BUILDER;
import com.beads.model.builder.ProductBuilder;
import com.beads.model.builder.ProductGroupPersistentBuilder;
import com.beads.model.domain.Product;
import com.beads.model.domain.ProductGroup;
import com.google.common.collect.ImmutableList;
import java.util.List;
import javax.annotation.Resource;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ProductDaoImplTest extends CommonDaoIT {

    @Autowired
    private ProductDao productDao;
    private ProductBuilder productBuilder;

    @Resource(name = PRODUCT_GROUP_PERSISTENT_BUILDER)
    private ProductGroupPersistentBuilder groupPersistentBuilder;

    @Before
    public void init() {
        productBuilder = new ProductBuilder();
    }

    @Test
    public void deleteProduct() {
        Product firstProduct = productBuilder.build();
        productDao.saveOrUpdate(firstProduct);
        productBuilder.reset();

        Product secondProduct = productBuilder.build();
        productDao.saveOrUpdate(secondProduct);
        productDao.removeProduct(firstProduct);

        List<Product> products = productDao.loadAllProducts();

        assertListSize(1, products);

        Assert.assertEquals("Actual result must be expected",
            secondProduct, products.get(0));
    }

    @Test
    public void testSearchProductBySearchString() {
        Product firstProduct = productBuilder.withArticul("AAD-5").build();
        productDao.saveOrUpdate(firstProduct);
        productBuilder.reset();

        Product secondProduct = productBuilder.withArticul("CCG-8").build();
        productDao.saveOrUpdate(secondProduct);
        List<Product> actualProducts = productDao.searchProductBySearchString("AA");

        assertListSize(1, actualProducts);

        Assert.assertEquals("Actual result must be expected",
            firstProduct, actualProducts.get(0));
    }

    @Test
    public void testLoadProductsByGroupId() {
        ProductGroup productGroup = groupPersistentBuilder.buildAndAddProductGroup();

        Product firstProduct = productBuilder.withProductGroup(productGroup.getId()).build();
        productDao.saveOrUpdate(firstProduct);
        productBuilder.reset();

        Product secondProduct = productBuilder.withProductGroup(productGroup.getId()).build();
        productDao.saveOrUpdate(secondProduct);

        List<Product> products = productDao.loadProductByGroupId(productGroup.getId());

        assertListSize(2, products);

        Assert.assertEquals("Actual result must be expected" ,
            ImmutableList.of(firstProduct, secondProduct), products);
    }

    @Test
    public void testSaveProduct() {
        Product expectedProduct = productBuilder.build();
        productDao.saveOrUpdate(expectedProduct);

        Product actualProduct = productDao.loadProductById(expectedProduct.getId());

        Assert.assertEquals("Actual result must be expected",
            actualProduct, expectedProduct);
    }

    @Test
    public void testLoadProductsWithOrderById() {
        Product firstProduct = productBuilder.build();
        productDao.saveOrUpdate(firstProduct);
        productBuilder.reset();

        Product secondProduct = productBuilder.build();
        productDao.saveOrUpdate(secondProduct);

        List<Product> actualProductsList = productDao.loadAllProducts();

        assertListSize(2, actualProductsList);

        Assert.assertEquals("Actual result must be expected" ,
            ImmutableList.of(secondProduct, firstProduct), actualProductsList);
    }

    private void assertListSize(int size, List list) {
        Assert.assertEquals("Actual products list size must be 2",
            size, list.size());
    }
}