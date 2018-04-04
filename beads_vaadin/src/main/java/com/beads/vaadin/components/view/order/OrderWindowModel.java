package com.beads.vaadin.components.view.order;

import static com.beads.vaadin.dao.OrderDaoImpl.BEAN_NAME;
import com.beads.model.domain.Order;
import com.beads.vaadin.dao.OrderDao;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.vaadin.spring.events.EventBus;

@Repository
public class OrderWindowModel {

  @Resource(name = BEAN_NAME)
  private OrderDao orderDao;

  @Autowired
  private EventBus eventBus;

  private Order order;

  public void saveOrder() {
    orderDao.saveOrUpdate(order);
    eventBus.publish(this, order);
  }

  public Order getOrder() {
    return order;
  }

  public void setOrder(Order order) {
    this.order = order;
  }
}
