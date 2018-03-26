package com.beads.model.dao;

import com.beads.model.domain.Product;
import com.beads.model.domain.ProductGroupView;
import java.util.List;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

/**
 * Created by alexey.dranchuk on 13.09.14.
 *
 */
@Repository
public class ProductDaoImpl extends BaseDao implements ProductDao {

    @Override
    public List<Product> loadAllProducts() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
        Root<Product> root = criteriaQuery.from(Product.class);
        criteriaQuery.select(root).orderBy(criteriaBuilder.desc(root.get(Product.ID)));
        return entityManager.createQuery(criteriaQuery).setMaxResults(MAX_ROW_RESULT).getResultList();
    }

    @Override
    public Product loadProductById(Integer id) {;
      return entityManager.find(Product.class, id);
    }

    @Override
    public List<Product> searchProductBySearchString(String searchString) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
        Root<Product> root = criteriaQuery.from(Product.class);
        criteriaQuery.select(root)
            .orderBy(criteriaBuilder.desc(root.get(Product.ID)))
            .where(criteriaBuilder.or(new Predicate[]{
                criteriaBuilder.like(root.get(Product.NAME), "%" + searchString + "%"),
                criteriaBuilder.like(root.get(Product.DESCRIPTION), "%" + searchString + "%"),
                criteriaBuilder.like(root.get(Product.ARTIKUL), "%" + searchString + "%"),
            }));
        return entityManager.createQuery(criteriaQuery).setMaxResults(MAX_ROW_RESULT).getResultList();
    }

    @Override
    public List<Product> loadProductByGroupId(Integer groupId) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
        Root<Product> root = criteriaQuery.from(Product.class);
        criteriaQuery.select(root)
            .where(criteriaBuilder.equal(root.get(Product.PRODUCT_GROUP_VIEW), groupId));
        return entityManager.createQuery(criteriaQuery).setMaxResults(MAX_ROW_RESULT).getResultList();
    }

    @Override
    public List<ProductGroupView> loadAllProductGroupView() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ProductGroupView> criteriaQuery = criteriaBuilder.createQuery(ProductGroupView.class);
        Root<ProductGroupView> root = criteriaQuery.from(ProductGroupView.class);
        criteriaQuery.select(root)
            .where(criteriaBuilder.isNotNull(root.get(ProductGroupView.PARENT_ID)));
        return entityManager.createQuery(criteriaQuery).setMaxResults(MAX_ROW_RESULT).getResultList();
    }

    @Override
    public void saveOrUpdate(Product product) {
        entityManager.persist(product);
    }

    @Override
    public void mergeProduct(Product product) {
        entityManager.merge(product);
    }

    @Override
    public void removeProduct(Product product) {
        Query query = entityManager.createQuery("delete from ProductPhoto where id = :id ");
        query.setParameter("id", product.getId());
        query.executeUpdate();
        entityManager.remove(product);
    }
}
