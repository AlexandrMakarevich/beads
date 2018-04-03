package com.beads.vaadin.dao;

import com.beads.model.domain.Order;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Repository(OrderDaoImpl.BEAN_NAME)
@Primary
public class OrderDaoImpl extends com.beads.model.dao.OrderDaoImpl implements OrderDao {

  public static final String BEAN_NAME = "vaadin.OrderDaoImpl";

  @Override
  public List<Order> getOrdersBySearchCriteria(SearchCriteria searchCriteria) {
    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
    Root<Order> root = criteriaQuery.from(Order.class);
    root.fetch(Order.ORDER_ITEMS, JoinType.LEFT);
    List<Predicate> predicatesOfSearchCriteria = buildSearchPredicate(criteriaBuilder, root, searchCriteria);
    Predicate[] predicates = predicatesOfSearchCriteria.toArray(new Predicate[predicatesOfSearchCriteria.size()]);
    criteriaQuery
        .select(root)
        .orderBy(criteriaBuilder.desc(root.get(Order.ID)));
    if (predicates.length != 0) {
      criteriaQuery.where(criteriaBuilder.and(predicates));
    }
    return entityManager.createQuery(criteriaQuery).setMaxResults(MAX_ROW_RESULT).getResultList();
  }

  @Override
  public Integer countOrdersBySearchCriteria(SearchCriteria searchCriteria) {
    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
    Root<Order> root = criteriaQuery.from(Order.class);
    List<Predicate> predicatesOfSearchCriteria = buildSearchPredicate(criteriaBuilder, root, searchCriteria);
    Predicate[] predicates = predicatesOfSearchCriteria.toArray(new Predicate[predicatesOfSearchCriteria.size()]);
    criteriaQuery
        .select(criteriaBuilder.count(root.get(Order.ID)));
    if (predicates.length != 0) {
      criteriaQuery.where(criteriaBuilder.and(predicates));
    }
    return entityManager.createQuery(criteriaQuery).getSingleResult().intValue();
  }

  private List<Predicate> buildSearchPredicate(
      CriteriaBuilder criteriaBuilder,
      Root<Order> root,
      SearchCriteria searchCriteria) {

    List<Predicate> predicates = new ArrayList<>();
    if (searchCriteria.isOrderIdNotNull()) {
      predicates.add(criteriaBuilder.equal(root.get(Order.ID), Integer.valueOf(searchCriteria.getOrderId())));
    }
    if (searchCriteria.isEmailNotNull()) {
      predicates.add(criteriaBuilder.like(root.get(Order.EMAIL),
          "%" + searchCriteria.getEmail() + "%"));
    }
    if (searchCriteria.isStatusNotNull()) {
      predicates.add(criteriaBuilder.equal(root.get(Order.STATUS), searchCriteria.getStatus()));
    }
    if (searchCriteria.isPhoneNumberNotNull()) {
      predicates.add(criteriaBuilder.like(root.get(Order.PHONE_NUMBER),
          "%" + searchCriteria.getPhoneNumber() + "%"));
    }
    if (searchCriteria.isDateOfOrderNotNull()) {
      predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(Order.MODIFIED_DATE),
          searchCriteria.getDateOfOrder()));
    }
    return predicates;
  }
}
