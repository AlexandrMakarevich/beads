package com.beads.vaadin.components.view.order;

import com.beads.model.domain.OrderStatus;
import com.beads.vaadin.dao.SearchCriteria;
import com.vaadin.data.ValidationException;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.shared.ui.datefield.DateTimeResolution;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateTimeField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import org.vaadin.spring.events.EventBus;

public class OrderSearchPanel extends HorizontalLayout {

  private EventBus eventBus;
  private TextField searchOrderIdField;
  private TextField searchEmailField;
  private TextField searchPhoneNumberField;
  private ComboBox<OrderStatus> searchStatusComboBox;
  private DateTimeField searchDateField;
  private Button searchButton;

  private SearchCriteria searchCriteria = new SearchCriteria();
  private SearchFieldsBinder searchFieldsBinder = new SearchFieldsBinder();
  public static final String SEARCH_FIELD_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

  public OrderSearchPanel(OrderGridModel orderGridModel, EventBus eventBus) {
    this.eventBus = eventBus;
    eventBus.subscribe(orderGridModel);
    addSearchComponents();
    setSpacing(true);
    setMargin(true);
  }

  private void addSearchComponents() {
    addSearchButton();
    addSearchIdField();
    addSearchEmailField();
    addSearchStatusComboBox();
    addSearchPhoneNumberField();
    addSearchDateField();

  }

  private void addSearchIdField() {
    searchOrderIdField = new TextField("№ заказа");
    searchFieldsBinder.searchBinderForId(searchOrderIdField, searchButton);
    addComponent(searchOrderIdField);
  }

  private void addSearchStatusComboBox() {
    searchStatusComboBox = new ComboBox<>("Состояние");
    searchStatusComboBox.setItems(OrderStatus.COMPLETE, OrderStatus.ERROR, OrderStatus.PENDING);
    searchFieldsBinder.searchBinderForStatus(searchStatusComboBox);
    addComponent(searchStatusComboBox);
  }

  private void addSearchEmailField() {
    searchEmailField = new TextField("Емейл заказчика");
    searchFieldsBinder.searchBinderForEmail(searchEmailField);
    addComponent(searchEmailField);
  }

  private void addSearchPhoneNumberField() {
    searchPhoneNumberField = new TextField("Номер телефона");
    searchFieldsBinder.searchBinderForPhoneNumber(searchPhoneNumberField);
    addComponent(searchPhoneNumberField);
  }

  private void addSearchDateField() {
    searchDateField = new DateTimeField("Дата заказа");
    searchDateField.setDateFormat(SEARCH_FIELD_DATE_FORMAT);
    searchDateField.setResolution(DateTimeResolution.SECOND);
    searchFieldsBinder.searchBinderForDate(searchDateField);
    addComponent(searchDateField);
  }

  private void addSearchButton() {
    searchButton = new Button();
    searchButton.setIcon(VaadinIcons.SEARCH);
    searchButton.addClickListener((Button.ClickEvent event) -> {
      try {
        searchFieldsBinder.writeBean(searchCriteria);
      } catch (ValidationException e) {
        e.printStackTrace();
      }
      eventBus.publish(this, searchCriteria);
    });
    addComponent(searchButton);
    setComponentAlignment(searchButton, Alignment.BOTTOM_CENTER);
  }
}
