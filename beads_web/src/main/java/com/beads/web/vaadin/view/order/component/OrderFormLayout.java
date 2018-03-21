package com.beads.web.vaadin.view.order.component;

import com.beads.model.domain.Order;
import com.beads.model.domain.OrderStatus;
import com.beads.web.vaadin.components.BeadsButton;
import com.beads.web.vaadin.components.BeadsComboBox;
import com.beads.web.vaadin.components.BeadsTextField;
import com.beads.web.vaadin.components.common.BeadsBeanFieldGroup;
import com.beads.web.vaadin.view.order_item.component.OrderItemTable;
import com.beads.web.vaadin.view.order_item.component.OrderItemTableModel;
import com.google.common.collect.ImmutableList;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Field;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;

public class OrderFormLayout extends FormLayout implements Button.ClickListener {

  private OrderWindowModel model;

  private OrderItemTableModel orderItemTableModel;

  private BeanFieldGroup<Order> orderBinder;

  private OrderItemTable orderItemTable;

  public OrderFormLayout(OrderWindowModel model) {
    this.model = model;
    setSpacing(true);
    setMargin(true);
    setDefaultComponentAlignment(Alignment.BOTTOM_CENTER);
    bindComponents();
    addButtons();
  }

  private void bindComponents() {
    orderBinder = new  BeadsBeanFieldGroup<>(model.getOrder(), OrderStatus.class);
    bindId();
    bindPhoneNumber();
    bindEmail();
    bindDeliveryAddress();
    bindOrderItem();
    bindDetails();
    bindTotalPrice();
    bindStatus();
  }

  private void addButtons() {
    HorizontalLayout horizontalLayout = new HorizontalLayout();
    horizontalLayout.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
    horizontalLayout.setSpacing(true);
    horizontalLayout.setMargin(false);
    addCloseButton(horizontalLayout);
    addSaveButton(horizontalLayout);
    addComponent(horizontalLayout);
  }

  private void addCloseButton(Layout layout) {
    Button closeButton = new Button("Закрыть", (Button.ClickListener) event -> closeParentWindow());
    closeButton.setClickShortcut(ShortcutAction.KeyCode.ESCAPE);
    layout.addComponent(closeButton);
  }

  private void addSaveButton(Layout layout) {
    Button button = new BeadsButton("Сохранить", model.getBeadsButtonModel(),  this);
    layout.addComponent(button);
  }

  private Field bindAndAddComponent(String caption, String propertyName, Class componentClass) {
    Field component = orderBinder.buildAndBind(caption, propertyName, componentClass);
    addComponent(component);
    return component;
  }

  private void bindId() {
    BeadsTextField id = (BeadsTextField) bindAndAddComponent("№", Order.ID, BeadsTextField.class);
    id.setEnabled(false);
  }

  private void bindPhoneNumber() {
    bindAndAddComponent("Телефон заказчика", Order.PHONE_NUMBER, BeadsTextField.class);
  }

  private void bindEmail() {
    bindAndAddComponent("Email заказчика", Order.EMAIL, BeadsTextField.class);
  }

  private void bindOrderItem() {
    orderItemTableModel = new OrderItemTableModel(model.getOrder());
    orderItemTable = new OrderItemTable(orderItemTableModel);
    orderBinder.bind(orderItemTable, Order.ORDER_ITEMS);
    addComponent(orderItemTable);
  }

  private void bindTotalPrice() {
    TextField textField = new TextField();
    textField.setCaption("Сумма заказа");
    textField.setValue(model.getOrder().getTotalPrice().toString());
    textField.setEnabled(false);
    addComponent(textField);
  }

  private void bindDeliveryAddress() {
    bindAndAddComponent("Адресс доставки", Order.DELIVERY_ADDRESS, BeadsTextField.class);
  }

  private void bindDetails() {
    bindAndAddComponent("Комментарии клиента", Order.ORDER_DETAILS, BeadsTextField.class);
  }

  @SuppressWarnings("unchecked")
  private void bindStatus() {
    BeadsComboBox statusBox = (BeadsComboBox<OrderStatus>)
        bindAndAddComponent("Статус заказа", Order.STATUS, BeadsComboBox.class);
    statusBox.addItems(ImmutableList.of(OrderStatus.COMPLETE,
        OrderStatus.ERROR, OrderStatus.PENDING));
  }

  private void closeParentWindow() {
    ((Window)getParent().getParent()).close();
  }

  @Override
  public void buttonClick(Button.ClickEvent event) {
    try {
      orderBinder.commit();
      model.saveOrder();
      closeParentWindow();
    } catch (FieldGroup.CommitException e) {
      Notification.show("Error", "Валидация не прошла", Notification.Type.ERROR_MESSAGE);
    }
  }
}
