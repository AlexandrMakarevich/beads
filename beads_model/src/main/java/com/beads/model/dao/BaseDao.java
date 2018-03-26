package com.beads.model.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by alexey.dranchuk on 28.09.14.
 *
 */
@Repository
@Transactional
public class BaseDao {

    @PersistenceContext
    protected EntityManager entityManager;

    public static final int MAX_ROW_RESULT = 10000;

}
