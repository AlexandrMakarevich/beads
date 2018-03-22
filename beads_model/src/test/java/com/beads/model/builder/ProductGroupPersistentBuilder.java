package com.beads.model.builder;

import static com.beads.model.builder.ProductGroupPersistentBuilder.PRODUCT_GROUP_PERSISTENT_BUILDER;
import com.beads.model.dao.ProductGroupDao;
import com.beads.model.dao.ProductGroupDaoImpl;
import com.beads.model.domain.ProductGroup;
import javax.annotation.Resource;
import org.springframework.stereotype.Repository;

@Repository(PRODUCT_GROUP_PERSISTENT_BUILDER)
public class ProductGroupPersistentBuilder {

  public static final String PRODUCT_GROUP_PERSISTENT_BUILDER = "productGroupPersistentBuilder";

  @Resource(name = ProductGroupDaoImpl.BEAN_NAME)
  private ProductGroupDao productGroupDao;

  private ProductGroupBuilder productGroupBuilder;

  public ProductGroup buildAndAddProductGroup() {
    productGroupBuilder = new ProductGroupBuilder();
    ProductGroup productGroup = productGroupBuilder.build();
    productGroupDao.saveOrUpdate(productGroup);
    return productGroup;
  }
}
