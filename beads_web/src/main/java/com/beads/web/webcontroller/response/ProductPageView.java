package com.beads.web.webcontroller.response;

import com.beads.model.domain.Product;
import com.google.common.collect.Lists;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by alexey.dranchuk on 30.09.14.
 *
 */
public class ProductPageView extends BasePageView {

    public static final int COLUMN_COUNT = 3;

    private List<Product> products = new LinkedList<>();

    @Override
    public int getSize() {
        return products.size();
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<List<Product>> getProductTableList() {
        return Lists.partition(products, COLUMN_COUNT);
    }
}
