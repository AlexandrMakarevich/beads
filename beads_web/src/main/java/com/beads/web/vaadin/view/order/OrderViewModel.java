package com.beads.web.vaadin.view.order;

import com.beads.web.vaadin.view.order.component.OrderTableModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Lazy
public class OrderViewModel {

  @Autowired
  private OrderTableModel orderTableModel;

  public OrderTableModel getOrderTableModel() {
    return orderTableModel;
  }
}
