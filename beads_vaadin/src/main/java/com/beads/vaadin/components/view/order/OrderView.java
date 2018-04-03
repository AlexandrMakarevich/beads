package com.beads.vaadin.components.view.order;

import static com.beads.vaadin.components.view.order.OrderGridModel.ORDER_GRID_MODEL;
import static com.beads.vaadin.components.view.order.OrderView.VIEW_NAME;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.events.EventBus;

@SpringView(name = VIEW_NAME)
public class OrderView extends Panel implements View {

  public static final String VIEW_NAME = "orderView";

  private Layout layout;

  @Resource(name = ORDER_GRID_MODEL)
  private OrderGridModel orderGridModel;

  @Autowired
  private EventBus eventBus;

  @PostConstruct
  public void addComponents() {
    initLayout();
    OrderSearchPanel orderSearchPanel = new OrderSearchPanel(orderGridModel,eventBus);
    orderSearchPanel.setCaption("<H2>Введите необходимые параметры для поиска :</H2>");
    orderSearchPanel.setCaptionAsHtml(true);
    layout.addComponent(orderSearchPanel);
    OrderGrid orderGrid = new OrderGrid(orderGridModel);
    layout.addComponent(orderGrid);
  }

  public void initLayout() {
    layout = new VerticalLayout();
    setContent(layout);
  }

  @Override
  public void enter(ViewChangeListener.ViewChangeEvent event) {
    setCaption("Работа с заказами");
  }
}
