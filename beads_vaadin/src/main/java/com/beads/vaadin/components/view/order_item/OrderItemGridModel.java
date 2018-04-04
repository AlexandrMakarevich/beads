package com.beads.vaadin.components.view.order_item;

import com.beads.model.domain.Order;
import com.beads.model.domain.OrderItem;
import com.vaadin.data.provider.AbstractBackEndDataProvider;
import com.vaadin.data.provider.Query;
import java.util.stream.Stream;
import org.vaadin.spring.events.Event;
import org.vaadin.spring.events.EventBusListener;

public class OrderItemGridModel extends AbstractBackEndDataProvider<OrderItem, Void>
    implements EventBusListener<Order> {

  public OrderItemGridModel(Order order) {
    this.order = order;
  }
  private Order order;

  @Override
  protected Stream<OrderItem> fetchFromBackEnd(Query<OrderItem, Void> query) {
    return order.getOrderItems().stream();
  }

  @Override
  protected int sizeInBackEnd(Query<OrderItem, Void> query) {
    return order.getOrderItems().size();
  }

  @Override
  public void onEvent(Event<Order> event) {
    this.order = event.getPayload();
    refreshAll();
  }
}
