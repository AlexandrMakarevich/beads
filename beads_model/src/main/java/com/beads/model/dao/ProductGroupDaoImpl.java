package com.beads.model.dao;

import com.beads.model.domain.Product;
import com.beads.model.domain.ProductGroup;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

/**
 * Created by alexey.dranchuk on 10.10.14.
 *
 */
@Repository(ProductGroupDaoImpl.BEAN_NAME)
public class ProductGroupDaoImpl extends BaseDao implements ProductGroupDao {

    public static final String BEAN_NAME = "ProductGroupDaoImpl";

    @Override
    public List<ProductGroup> findAllProductGroup() {
        return baseQuerySearch((criteriaBuilder, root) ->
            criteriaBuilder.isNull(root.get(ProductGroup.PARENT_PRODUCT_GROUP)));
    }

    @Override
    public List<ProductGroup> loadProductGroupsExcludeCurrent(ProductGroup productGroup) {
        int productId = productGroup.isNewProductGroup() ? 0 : productGroup.getId();
        return baseQuerySearch((criteriaBuilder, root) ->
            criteriaBuilder.notEqual(root.get(ProductGroup.ID), productId));
    }

    @Override
    public int saveOrUpdate(ProductGroup productGroup) {
        entityManager.persist(productGroup);
        return productGroup.getId();
    }

    @Override
    public ProductGroup loadProductGroupById(int productGroupId) {
        return entityManager.find(ProductGroup.class, productGroupId);
    }

    @Override
    public void deleteProductGroup(ProductGroup productGroup) {
        for(ProductGroup pg : productGroup.getChildGroups()) {
            entityManager.remove(pg);
        }
        entityManager.remove(productGroup);
    }

    @Override
    public List<ProductGroup> findProductGroupsByName(String searchString) {
        if (StringUtils.isNotBlank(searchString)) {
            return baseQuerySearch((criteriaBuilder, root) ->
                criteriaBuilder.and(criteriaBuilder.like(root.get(Product.NAME),
                    "%" + searchString + "%")));
        }
        return findAllProductGroup();
    }

    private List<ProductGroup> baseQuerySearch(CallBackFunc callBackFunc) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ProductGroup> criteriaQuery = criteriaBuilder.createQuery(ProductGroup.class);
        Root<ProductGroup> root = criteriaQuery.from(ProductGroup.class);
        criteriaQuery.select(root)
            .orderBy(criteriaBuilder.desc(root.get(ProductGroup.ORDER_ID)))
            .where(criteriaBuilder.and(callBackFunc.execute(criteriaBuilder, root)));
        return entityManager.createQuery(criteriaQuery).setMaxResults(MAX_ROW_RESULT).getResultList();
    }

    private interface CallBackFunc {

        Predicate execute(CriteriaBuilder criteriaBuilder, Root<ProductGroup> root);
    }
}
