package com.beads.model.dao;

import com.beads.model.domain.ProductGroup;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.NullPrecedence;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Created by alexey.dranchuk on 10.10.14.
 *
 */
@Repository(ProductGroupDaoImpl.BEAN_NAME)
public class ProductGroupDaoImpl extends BaseDao implements ProductGroupDao {

    public static final String BEAN_NAME = "ProductGroupDaoImpl";

    @Override
    public List<ProductGroup> findAllProductGroup() {
        return baseQuerySearch(Restrictions.isNull(ProductGroup.PARENT_PRODUCT_GROUP));
    }

    @Override
    public List<ProductGroup> loadProductGroupsExcludeCurrent(ProductGroup productGroup) {
        int productId = productGroup.isNewProductGroup() ? 0 : productGroup.getId();
        return baseQuerySearch(Restrictions.ne(ProductGroup.ID, productId));
    }

    @Override
    public int saveOrUpdate(ProductGroup productGroup) {
        getSession().saveOrUpdate(productGroup);
        return productGroup.getId();
    }

    @Override
    public ProductGroup loadProductGroupById(int productGroupId) {
        return (ProductGroup) getSession().get(ProductGroup.class, productGroupId);
    }

    @Override
    public void deleteProductGroup(ProductGroup productGroup) {
        for(ProductGroup pg : productGroup.getChildGroups()) {
            getSession().delete(pg);
        }
        getSession().delete(productGroup);
    }

    @Override
    public List<ProductGroup> findProductGroupsByName(String searchString) {
        if (StringUtils.isNotBlank(searchString)) {
            return baseQuerySearch(Restrictions.like(ProductGroup.NAME, searchString, MatchMode.ANYWHERE));
        }
        return findAllProductGroup();
    }

    @SuppressWarnings("unchecked")
    private List<ProductGroup> baseQuerySearch(Criterion criterion) {
        Criteria crt = getSession().createCriteria(ProductGroup.class);
        crt.add(criterion);
        crt.addOrder(Order.desc(ProductGroup.ORDER_ID)
                          .nulls(NullPrecedence.LAST));
        crt.setMaxResults(MAX_ROW_RESULT);
        return crt.list();
    }
}
