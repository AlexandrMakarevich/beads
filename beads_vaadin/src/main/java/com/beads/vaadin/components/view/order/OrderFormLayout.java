package com.beads.vaadin.components.view.order;

import com.beads.model.domain.Order;
import com.beads.model.domain.OrderStatus;
import com.beads.vaadin.components.view.order_item.OrderItemGrid;
import com.beads.vaadin.components.view.order_item.OrderItemGridModel;
import com.google.common.collect.ImmutableList;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;

public class OrderFormLayout extends FormLayout implements Button.ClickListener {

  private OrderWindowModel orderWindowModel;
  private Binder<Order> orderBinder = new Binder<>();

  public OrderFormLayout(OrderWindowModel orderWindowModel) {
    this.orderWindowModel = orderWindowModel;
    setSpacing(true);
    setMargin(true);
    setDefaultComponentAlignment(Alignment.BOTTOM_CENTER);
    addComponents();
    addButtons();
  }

  private void addComponents() {
   bindAndAddIdField();
   bindAndAddPhoneNumberField();
   bindAndAddEmailField();
   bindAndAddDeliveryAddress();
   bindAndAddOrderItem();
   bindAndAddDetailsField();
   bindTotalPriceField();
   bindAndAddStatus();
   orderBinder.readBean(orderWindowModel.getOrder());
  }

  private void addButtons() {
    HorizontalLayout layout = new HorizontalLayout();
    layout.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
    layout.setSpacing(true);
    layout.setMargin(false);
    addCloseButton(layout);
    addSaveButton(layout);
    addComponent(layout);
  }

  private void addSaveButton(HorizontalLayout horizontalLayout) {
    Button closeButton = new Button("Закрыть", (Button.ClickListener) event -> closeParentWindow());
    closeButton.setIcon(VaadinIcons.CLOSE);
    closeButton.setClickShortcut(ShortcutAction.KeyCode.ESCAPE);
    horizontalLayout.addComponent(closeButton);
  }

  private void addCloseButton(HorizontalLayout horizontalLayout) {
    Button saveButton = new Button("Сохранить", this);
    saveButton.setIcon(VaadinIcons.SAFE);
    horizontalLayout.addComponent(saveButton);
  }

  private void bindAndAddIdField() {
    TextField idField = new TextField("№");
    idField.setWidth("50px");
    orderBinder.forField(idField)
        .withConverter(new StringToIntegerConverter("Value can't be string"))
        .bind(Order::getId, Order::setId);
    idField.setEnabled(false);
    addComponent(idField);
  }

  private void bindAndAddPhoneNumberField() {
    TextField phoneNumberField = new TextField("Телефон заказчика");
    phoneNumberField.setWidth("250px");
    orderBinder.forField(phoneNumberField)
        .bind(Order::getPhoneNumber, Order::setPhoneNumber);
    addComponent(phoneNumberField);
  }

  private void bindAndAddEmailField() {
    TextField emailField = new TextField("Email заказчика");
    emailField.setWidth("250px");
    orderBinder.forField(emailField)
        .bind(Order::getEmail, Order::setEmail);
    addComponent(emailField);
  }

  private void bindTotalPriceField() {
    TextField totalPriceField = new TextField("Сумма заказа");
    totalPriceField.setValue(orderWindowModel.getOrder().getTotalPrice().toString());
    totalPriceField.setEnabled(false);
    addComponent(totalPriceField);
  }

  private void bindAndAddDeliveryAddress() {
    TextField deliveryAddressField = new TextField("Адресс доставки");
    deliveryAddressField.setWidth("250px");
    orderBinder.forField(deliveryAddressField)
        .bind(Order::getDeliveryAddress, Order::setDeliveryAddress);
    addComponent(deliveryAddressField);
  }

  private void bindAndAddStatus() {
    ComboBox<OrderStatus> statusComboBox = new ComboBox<>("Статус заказа");
    statusComboBox.setItems(ImmutableList.of(OrderStatus.COMPLETE,
        OrderStatus.ERROR, OrderStatus.PENDING));
    orderBinder.forField(statusComboBox)
        .bind(Order::getStatus, Order::setStatus);
    addComponent(statusComboBox);
  }

  private void bindAndAddDetailsField() {
    TextField detailsField = new TextField("Комментарии клиента");
    detailsField.setWidth("250px");
    orderBinder.forField(detailsField)
        .bind(Order::getOrderDetails, Order::setOrderDetails);
    addComponent(detailsField);
  }

  private void bindAndAddOrderItem() {
    OrderItemGridModel orderItemGridModel = new OrderItemGridModel(orderWindowModel.getOrder());
    OrderItemGrid orderItemGrid = new OrderItemGrid(orderItemGridModel);
    addComponent(orderItemGrid);
  }

  private void closeParentWindow() {
    ((Window)getParent().getParent()).close();
  }

  @Override
  public void buttonClick(Button.ClickEvent event) {
    try {
      orderBinder.writeBean(orderWindowModel.getOrder());
      orderWindowModel.saveOrder();
      closeParentWindow();
    } catch (ValidationException e) {
      Notification.show("Error", "Валидация не прошла", Notification.Type.ERROR_MESSAGE);
    }
  }
}
