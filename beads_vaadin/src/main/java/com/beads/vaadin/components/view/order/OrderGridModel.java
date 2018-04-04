package com.beads.vaadin.components.view.order;

import static com.beads.vaadin.components.view.order.OrderGridModel.ORDER_GRID_MODEL;
import static com.beads.vaadin.dao.OrderDaoImpl.BEAN_NAME;
import com.beads.model.domain.Order;
import com.beads.vaadin.dao.OrderDao;
import com.beads.vaadin.dao.SearchCriteria;
import com.vaadin.data.provider.AbstractBackEndDataProvider;
import com.vaadin.data.provider.Query;
import com.vaadin.ui.Grid;
import com.vaadin.ui.UI;
import com.vaadin.ui.components.grid.ItemClickListener;
import java.time.LocalDateTime;
import java.util.stream.Stream;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.vaadin.spring.events.Event;
import org.vaadin.spring.events.EventBusListener;

@Component(ORDER_GRID_MODEL)
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class OrderGridModel extends AbstractBackEndDataProvider<Order, Void>
    implements EventBusListener<SearchCriteria>, ItemClickListener {

  public static final String ORDER_GRID_MODEL = "orderGridModel";

  @Resource(name = BEAN_NAME)
  private OrderDao orderDao;

  @Autowired
  private OrderWindowModel model;

  private SearchCriteria searchCriteria;

  @PostConstruct
  private void init() {
    searchCriteria = new SearchCriteria();
    searchCriteria.setDateOfOrder(LocalDateTime.now());
  }

  @Override
  protected Stream<Order> fetchFromBackEnd(Query<Order, Void> query) {
    return orderDao.getOrdersBySearchCriteria(searchCriteria).stream();
  }

  @Override
  protected int sizeInBackEnd(Query<Order, Void> query) {
    return orderDao.countOrdersBySearchCriteria(searchCriteria);
  }

  @Override
  public void onEvent(Event<SearchCriteria> event) {
    this.searchCriteria = event.getPayload();
    refreshAll();
  }

  @Override
  public void itemClick(Grid.ItemClick event) {
    Order selectedOrder = (Order) event.getItem();
    if (event.getMouseEventDetails().isDoubleClick()) {
      showEditOrder(selectedOrder);
    }
  }

  private void showEditOrder(Order selectedOrder) {
    model.setOrder(selectedOrder);
    OrderWindow orderWindow = new OrderWindow(model);
    UI.getCurrent().addWindow(orderWindow);
  }
}
