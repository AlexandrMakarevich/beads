package com.beads.model.builder;

import com.beads.model.domain.ProductGroup;
import java.util.List;

public class ProductGroupBuilder {

  private ProductGroup productGroup;

  public ProductGroupBuilder() {
    init();
  }

  public void init() {
    productGroup = new ProductGroup();
    withName("Group name");
  }

  public ProductGroupBuilder withName(String name) {
    productGroup.setName(name);
    return this;
  }

  public ProductGroupBuilder withListChildGroup(List<ProductGroup> childGroups) {
    productGroup.setChildGroups(childGroups);
    return this;
  }

  public ProductGroup build() {
    return productGroup;
  }
}
