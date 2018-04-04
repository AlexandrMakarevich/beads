package com.beads.vaadin.components.view.order;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Window;

public class OrderWindow extends Window {

  public OrderWindow(OrderWindowModel model) {
    super("Заказ № " + model.getOrder().getId());
    setModal(true);
    setHeight(440, Unit.POINTS);
    setWidth(620, Unit.POINTS);
    center();
    initLayout(model);
  }

  private void initLayout(OrderWindowModel model) {
    HorizontalLayout hl = new HorizontalLayout();
    hl.addComponent(new OrderFormLayout(model));
    setContent(hl);
  }
}
