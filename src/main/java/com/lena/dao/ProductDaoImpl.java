package com.lena.dao;

import com.lena.domain.Product;
import com.lena.domain.ProductGroupView;
import org.hibernate.Criteria;
import org.hibernate.criterion.*;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Created by Administrator on 13.09.14.
 */
@Repository
public class ProductDaoImpl extends BaseDao implements ProductDao {

    @Override
    public List<Product> findAllProducts() {
        Criteria crt = getSession().createCriteria(Product.class);
        crt.addOrder(Order.desc(Product.ID));
        crt.setMaxResults(MAX_ROW_RESULT);
        return crt.list();
    }

    @Override
    public Product loadProductById(Integer id) {
        return (Product) getSession().get(Product.class, id);
    }

    @Override
    public List<Product> searchProductBySearchString(String searchString) {
        Criteria crt = getSession().createCriteria(Product.class);
        crt.add(buildSearchRestriction(searchString));
        crt.addOrder(Order.desc(Product.ID));
        crt.setMaxResults(MAX_ROW_RESULT);
        return crt.list();
    }

    @Override
    public List<Product> loadProductByGroupId(Integer groupId) {
        Criteria crt = getSession().createCriteria(Product.class);
        crt.add(Restrictions.eq(Product.GROUP_ID, groupId));
        crt.setMaxResults(MAX_ROW_RESULT);
        return crt.list();
    }

    @Override
    public List<ProductGroupView> loadAllProductGroupView() {
        Criteria crt = getSession().createCriteria(ProductGroupView.class);
        crt.add(Restrictions.isNotNull(ProductGroupView.PARENT_ID));
        crt.setMaxResults(MAX_ROW_RESULT);
        return crt.list();
    }

    @Override
    public void saveOrUpdate(Product product) {
        getSession().saveOrUpdate(product);
    }

    @Override
    public void removeProduct(Product product) {
        getSession().delete(product);
    }

    private Criterion buildSearchRestriction(String searchString) {
        Disjunction dj = Restrictions.disjunction();
        Integer id = parseToInteger(searchString);
        if (id != null) {
            dj.add(Restrictions.eq(Product.ID, id));
        }
        dj.add(Restrictions.ilike(Product.NAME, searchString, MatchMode.ANYWHERE));
        dj.add(Restrictions.ilike(Product.DESCRIPTION, searchString, MatchMode.ANYWHERE));
        return dj;
    }

    private Integer parseToInteger(String searchString) {
        try {
            return Integer.valueOf(searchString);
        } catch (NumberFormatException e) {
            return null;
        }
    }


}
