package com.beads.vaadin.components.view.order_item;

import com.beads.model.domain.OrderItem;
import com.vaadin.ui.Grid;

public class OrderItemGrid extends Grid<OrderItem> {

  public OrderItemGrid(OrderItemGridModel orderItemGridModel) {
    setDataProvider(orderItemGridModel);
    setCaption("Список заказов");
    setSizeFull();
    setHeight(100, Unit.POINTS);
    setWidth(400, Unit.POINTS);
    initColumn();
  }

  private void initColumn() {
    addColumn(OrderItem::getId).setCaption("№");
    addColumn(OrderItem::getArticul).setCaption("Артикул");
    addColumn(OrderItem::getQuantity).setCaption("Количество");
    addColumn(orderItem -> orderItem.getProduct().getName()).setCaption("Продукт");
    addColumn(orderItem -> orderItem.getProduct().getPrice()).setCaption("Цена");
  }
}
