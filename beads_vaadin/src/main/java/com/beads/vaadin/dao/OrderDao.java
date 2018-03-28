package com.beads.vaadin.dao;

import com.beads.model.domain.Order;
import java.util.List;

public interface OrderDao extends com.beads.model.dao.OrderDao {


  /**
   * This method find orders matched {@link SearchCriteria}.
   *
   * @param searchCriteria criteria by search Order
   *
   * @return the result list
   */
  List<Order> getOrdersBySearchCriteria(SearchCriteria searchCriteria);

  Integer countOrdersBySearchCriteria(SearchCriteria searchCriteria);

}
