package com.beads.vaadin.components.view.order;

import static com.beads.vaadin.components.view.order.OrderGridModel.ORDER_GRID_MODEL;
import static com.beads.vaadin.dao.OrderDaoImpl.BEAN_NAME;
import com.beads.model.domain.Order;
import com.beads.vaadin.dao.OrderDao;
import com.beads.vaadin.dao.SearchCriteria;
import com.vaadin.data.provider.AbstractBackEndDataProvider;
import com.vaadin.data.provider.Query;
import java.time.LocalDateTime;
import java.util.stream.Stream;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

@Component(ORDER_GRID_MODEL)
public class OrderGridModel extends AbstractBackEndDataProvider<Order, Void> {

  public static final String ORDER_GRID_MODEL = "orderGridModel";

  @Resource(name = BEAN_NAME)
  private OrderDao orderDao;

  @Override
  protected Stream<Order> fetchFromBackEnd(Query<Order, Void> query) {
    SearchCriteria searchCriteria = new SearchCriteria();
    searchCriteria.setDateOfOrder(LocalDateTime.now());
    return orderDao.getOrdersBySearchCriteria(searchCriteria).stream();
  }

  @Override
  protected int sizeInBackEnd(Query<Order, Void> query) {
    SearchCriteria searchCriteria = new SearchCriteria();
    searchCriteria.setDateOfOrder(LocalDateTime.now());
    return orderDao.countOrdersBySearchCriteria(searchCriteria);
  }
}
