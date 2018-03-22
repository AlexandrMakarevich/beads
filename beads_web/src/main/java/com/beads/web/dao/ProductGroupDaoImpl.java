package com.beads.web.dao;

import com.beads.web.constant.Constant;
import com.beads.model.domain.ProductGroup;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;

/**
 * Created by alexey.dranchuk on 31/1/15.
 *
 */

@Repository(ProductGroupDaoImpl.BEAN_NAME)
public class ProductGroupDaoImpl extends com.beads.model.dao.ProductGroupDaoImpl {

    public static final String BEAN_NAME = "web.productGroupDaoImpl";


    @Override
    @PreAuthorize(Constant.HAS_ROLE_ADMIN)
    public int saveOrUpdate(ProductGroup productGroup) {
        super.saveOrUpdate(productGroup);
        return productGroup.getId();
    }

    @Override
    @PreAuthorize(Constant.HAS_ROLE_ADMIN)
    public void deleteProductGroup(ProductGroup productGroup) {
        super.deleteProductGroup(productGroup);
    }
}
