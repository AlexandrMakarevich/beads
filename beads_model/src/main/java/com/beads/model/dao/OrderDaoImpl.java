package com.beads.model.dao;

import com.beads.model.domain.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * Created by alexey.dranchuk on 28.09.14.
 *
 */
@Repository
public class OrderDaoImpl extends BaseDao implements OrderDao {

    public static final Logger LOG = LoggerFactory.getLogger(OrderDaoImpl.class);

    @Override
    public int saveOrUpdate(Order order) {
        entityManager.persist(order);
        return order.getId();
    }

    @Override
    public Order loadOrderById(int orderId) {
        return entityManager.find(Order.class, orderId);
    }
}
