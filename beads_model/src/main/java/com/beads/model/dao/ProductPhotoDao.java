package com.beads.model.dao;

import com.beads.model.domain.ProductPhoto;
import org.springframework.stereotype.Repository;

/**
 * Created by alexey.dranchuk on 01.11.14.
 *
 */
@Repository
public class ProductPhotoDao extends BaseDao {

    public ProductPhoto getProductPhotoById(int productId) {
        return entityManager.find(ProductPhoto.class, productId);
    }

    public void saveOrUpdate(ProductPhoto productPhoto) {
        entityManager.persist(productPhoto);
    }
}
