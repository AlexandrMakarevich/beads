package com.beads.vaadin.components.view.order;

import com.beads.model.domain.Order;
import com.vaadin.ui.Grid;

public class OrderGrid extends Grid<Order> {

  public OrderGrid(OrderGridModel orderGridModel) {
    setDataProvider(orderGridModel);
    initColumn();
    setSizeFull();
  }

  public void initColumn() {
    addColumn(Order::getId).setCaption("№");
    addColumn(Order::getEmail).setCaption("Емейл заказчика");
    addColumn(Order::getStatus).setCaption("Состояние");
    addColumn(Order::getPhoneNumber).setCaption("Номер телефона");
    addColumn(Order::getModifyDate).setCaption("Дата заказа");
  }
}
