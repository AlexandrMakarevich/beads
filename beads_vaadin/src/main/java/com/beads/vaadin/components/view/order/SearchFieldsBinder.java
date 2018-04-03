package com.beads.vaadin.components.view.order;

import com.beads.model.domain.OrderStatus;
import com.beads.vaadin.dao.SearchCriteria;
import com.vaadin.data.Binder;
import com.vaadin.data.HasValue;
import com.vaadin.server.SerializablePredicate;
import com.vaadin.ui.Button;
import java.time.LocalDateTime;

public class SearchFieldsBinder extends Binder<SearchCriteria> {

  public void searchBinderForId(HasValue<String> idField, Button searchButton) {
    forField(idField)
        .withValidator((SerializablePredicate<String>) value -> value.length() == 0 ||
                Integer.valueOf(value) > 0
            ,
            "Search must be positive integer values"
        )
        .bind(SearchCriteria::getOrderId, SearchCriteria::setOrderId);
    addStatusChangeListener(event -> {
      boolean isValid = event.getBinder().isValid();
      searchButton.setEnabled(isValid);
    });
  }

  public void searchBinderForStatus(HasValue<OrderStatus> statusField) {
    forField(statusField)
        .bind(SearchCriteria::getStatus, SearchCriteria::setStatus);
  }

  public void searchBinderForEmail(HasValue<String> emailField) {
    forField(emailField)
        .bind(SearchCriteria::getEmail, SearchCriteria::setEmail);
  }

  public void searchBinderForPhoneNumber(HasValue<String> phoneNumberField) {
    forField(phoneNumberField)
        .bind(SearchCriteria::getPhoneNumber, SearchCriteria::setPhoneNumber);
  }

  public void searchBinderForDate(HasValue<LocalDateTime> dateField) {
    forField(dateField)
        .bind(SearchCriteria::getDateOfOrder, SearchCriteria::setDateOfOrder);
  }
}
