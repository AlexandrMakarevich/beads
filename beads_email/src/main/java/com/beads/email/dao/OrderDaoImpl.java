package com.beads.email.dao;

import com.beads.model.domain.Order;
import com.beads.model.domain.OrderStatus;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.type.IntegerType;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import java.util.List;

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
        SQLQuery query = getSession().createSQLQuery("select id from `order` where status = :status order by id asc");
        query.setParameter(Order.STATUS, OrderStatus.PENDING.getDbValue());
        query.addScalar(Order.ID, IntegerType.INSTANCE);
        query.setMaxResults(MAX_ROW_RESULT);
        return query.list();
    }

    @Override
    public Order loadOrderById(int orderId) {
        Order order = super.loadOrderById(orderId);
        Hibernate.initialize(order.getProducts());
        getSession().evict(order);
        return order;
    }
}
