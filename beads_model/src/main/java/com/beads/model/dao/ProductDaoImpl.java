package com.beads.model.dao;

import com.beads.model.domain.Product;
import com.beads.model.domain.ProductGroupView;
import javax.persistence.criteria.*;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Created by alexey.dranchuk on 13.09.14.
 *
 */
@Repository
public class ProductDaoImpl extends BaseDao implements ProductDao {

    @Override
    public List<Product> loadAllProducts() {
        CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();
        CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
        Root<Product> root = criteriaQuery.from(Product.class);
        criteriaQuery.select(root).orderBy(criteriaBuilder.desc(root.get(Product.ID)));
        return getSession().createQuery(criteriaQuery).setMaxResults(MAX_ROW_RESULT).list();
    }

    @Override
    public Product loadProductById(Integer id) {
        return getSession().get(Product.class, id);
    }

    @Override
    public List<Product> searchProductBySearchString(String searchString) {
        CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();
        CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
        Root<Product> root = criteriaQuery.from(Product.class);
        criteriaQuery.select(root)
            .orderBy(criteriaBuilder.desc(root.get(Product.ID)))
            .where(criteriaBuilder.or(new Predicate[]{
                criteriaBuilder.like(root.get(Product.NAME), "%" + searchString + "%"),
                criteriaBuilder.like(root.get(Product.DESCRIPTION), "%" + searchString + "%"),
                criteriaBuilder.like(root.get(Product.ARTIKUL), "%" + searchString + "%"),
            }));
        return getSession().createQuery(criteriaQuery).setMaxResults(MAX_ROW_RESULT).list();
    }

    @Override
    public List<Product> loadProductByGroupId(Integer groupId) {
        CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();
        CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
        Root<Product> root = criteriaQuery.from(Product.class);
        criteriaQuery.select(root)
            .where(criteriaBuilder.equal(root.get(Product.PRODUCT_GROUP_VIEW), groupId));
        return getSession().createQuery(criteriaQuery).setMaxResults(MAX_ROW_RESULT).list();
    }

    @Override
    public List<ProductGroupView> loadAllProductGroupView() {
        CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();
        CriteriaQuery<ProductGroupView> criteriaQuery = criteriaBuilder.createQuery(ProductGroupView.class);
        Root<ProductGroupView> root = criteriaQuery.from(ProductGroupView.class);
        criteriaQuery.select(root)
            .where(criteriaBuilder.isNotNull(root.get(ProductGroupView.PARENT_ID)));
        return getSession().createQuery(criteriaQuery).setMaxResults(MAX_ROW_RESULT).list();
    }

    @Override
    public void saveOrUpdate(Product product) {
        getSession().saveOrUpdate(product);
    }

    @Override
    public void removeProduct(Product product) {
        Query query = getSession().createQuery("delete from ProductPhoto where id = :id ");
        query.setParameter("id", product.getId());
        query.executeUpdate();
        getSession().delete(product);
    }
}
