package com.lena.dao;

import com.lena.domain.ProductGroup;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Administrator on 10.10.14.
 */
public interface ProductGroupDao {

    List<ProductGroup> findAllProductGroup();

    void saveProductGroup(ProductGroup productGroup);

    ProductGroup loadProductGroupById(int productGroupId);
}
