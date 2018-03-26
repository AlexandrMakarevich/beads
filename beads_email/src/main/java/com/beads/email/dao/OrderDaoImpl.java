package com.beads.email.dao;

import com.beads.model.domain.Order;
import com.beads.model.domain.OrderStatus;
import java.util.List;
import javax.persistence.Query;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

/**
 * Created by alexey.dranchuk on 24/1/15.
 *
 */

@Repository(OrderDaoImpl.BEAN_NAME)
@Primary
public class OrderDaoImpl extends com.beads.model.dao.OrderDaoImpl implements OrderDao {

    public static final String BEAN_NAME = "email.OrderDaoImpl";

    @Override
    @SuppressWarnings("unchecked")
    public List<Integer> loadPendingOrderIds() {
        Query query = entityManager.createNamedQuery("loadPendingOrdersIds", Integer.class)
            .setParameter(Order.STATUS, OrderStatus.PENDING)
            .setMaxResults(MAX_ROW_RESULT);
        return query.getResultList();
    }
}
