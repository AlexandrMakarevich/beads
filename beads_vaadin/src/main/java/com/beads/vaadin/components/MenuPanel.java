package com.beads.vaadin.components;

import com.beads.vaadin.components.commons.MenuItemClickListener;
import com.beads.vaadin.components.view.order.OrderView;
import com.vaadin.ui.Button;
import com.vaadin.ui.VerticalLayout;

public class MenuPanel extends VerticalLayout {

  public MenuPanel() {
    setSpacing(true);
    setMargin(true);
    initComponents();
  }

  private void initComponents() {
    addComponent(new Button("Группы продуктов"));
    addComponent(new Button("Товар"));
    addComponent(new Button("Заказы", new MenuItemClickListener(OrderView.VIEW_NAME)));
    addComponent(new Button("Выход"));
  }
}
