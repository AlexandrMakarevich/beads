package com.beads.model.dao;

import com.beads.model.domain.Product;
import com.beads.model.domain.ProductGroupView;
import java.util.List;

/**
 * Created by alexey.dranchuk on 13.09.14.
 *
 */
public interface ProductDao {

    List<Product> loadAllProducts();

    Product loadProductById(Integer id);

    List<Product> searchProductBySearchString(String searchString);

    List<Product> loadProductByGroupId(Integer groupId);

    List<ProductGroupView> loadAllProductGroupView();

    void mergeProduct(Product product);

    void saveOrUpdate(Product product);

    void removeProduct(Product product);
}
