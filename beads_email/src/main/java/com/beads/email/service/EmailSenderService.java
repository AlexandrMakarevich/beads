package com.beads.email.service;

import com.beads.email.dao.OrderDao;
import com.beads.email.dao.OrderDaoImpl;
import com.beads.email.util.Batch;
import com.beads.model.domain.Order;
import com.beads.model.domain.OrderStatus;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;

/**
 * Created by alexey.dranchuk on 26/1/15.
 *
 */

@Service
public class EmailSenderService {

    @Resource(name = OrderDaoImpl.BEAN_NAME)
    private OrderDao orderDao;

    @Autowired
    private EmailSender emailSender;

    public void sendEmail(Batch batch) {
        for(Integer orderId : batch.getIds()) {
            Order order = processOrder(orderId);
            updateOrder(order);

        }
    }

    private Order processOrder(Integer orderId) {
        Order order = orderDao.loadOrderById(orderId);
        OrderStatus status = OrderStatus.COMPLETE;
        if (!emailSender.sendEmail(order)) {
            status = OrderStatus.ERROR;
        }
        order.setStatus(status);
        return order;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    private void updateOrder(Order order) {
        order.setModifyDate(new DateTime());
        orderDao.saveOrUpdate(order);
    }

}
