package com.beads.web.vaadin.view.product.component;

import com.beads.model.domain.Product;
import com.beads.model.domain.ProductGroupView;
import com.beads.web.vaadin.components.BeadsButton;
import com.beads.web.vaadin.components.BeadsComboBox;
import com.beads.web.vaadin.components.BeadsTextArea;
import com.beads.web.vaadin.components.BeadsTextField;
import com.beads.web.vaadin.components.common.BeadsBeanFieldGroup;
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
import com.vaadin.ui.Window;

/**
 * Created by alexey.dranchuk on 9/1/15.
 *
 */
public class ProductFormLayout extends FormLayout implements Button.ClickListener {

    private ProductWindowModel model;

    private BeanFieldGroup<Product> binder;

    public ProductFormLayout(ProductWindowModel model) {
        this.model = model;
        setSpacing(true);
        setMargin(true);
        setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        bindComponents();
        addButtons();
    }

    private void addButtons() {
        HorizontalLayout hl = new HorizontalLayout();
        hl.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
        hl.setSpacing(true);
        hl.setMargin(false);
        addSaveButton(hl);
        addCloseButton(hl);
        addComponent(hl);
    }

    private void addCloseButton(Layout hl) {
        Button closeButton = new Button("Закрыть", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                closeParentWindow();
            }
        });
        closeButton.setClickShortcut(ShortcutAction.KeyCode.ESCAPE);
        hl.addComponent(closeButton);
    }

    private void addSaveButton(HorizontalLayout layout) {
        Button saveButton = new BeadsButton("Сохранить", model.getSaveButtonModel(), this);
        layout.addComponent(saveButton);
    }

    private void closeParentWindow() {
        ((Window)getParent().getParent()).close();
    }

    @SuppressWarnings("unchecked")
    private void bindComponents() {
        binder = new BeadsBeanFieldGroup<>(model.getProduct(), ProductGroupView.class);
        bindId();
        bindProductCode();
        bindPrice();
        bindName();
        bindParentGroup();
        bindDescription();
    }

    private Field bindAndAddComponent(String caption, String propertyName, Class componentClass) {
        Field component = binder.buildAndBind(caption, propertyName, componentClass);
        addComponent(component);
        return component;
    }

    private void bindDescription() {
        bindAndAddComponent("Описание", Product.DESCRIPTION, BeadsTextArea.class);
    }

    private void bindPrice() {
        bindAndAddComponent("Цена", Product.PRICE, BeadsTextField.class);
    }

    private void bindProductCode() {
        bindAndAddComponent("Артикул", Product.ARTIKUL, BeadsTextField.class);
    }

    @SuppressWarnings("unchecked")
    private void bindParentGroup() {
        BeadsComboBox parent = (BeadsComboBox<ProductGroupView>) bindAndAddComponent("Группа товара", Product.PRODUCT_GROUP_VIEW, BeadsComboBox.class);
        parent.addItems(model.loadProductGroupViews());
    }

    private void bindName() {
        bindAndAddComponent("Имя продукта", Product.NAME, BeadsTextField.class);
    }

    private void bindId() {
        BeadsTextField id = (BeadsTextField) bindAndAddComponent("№", Product.ID, BeadsTextField.class);
        id.setEnabled(false);
    }

    @Override
    public void buttonClick(Button.ClickEvent event) {
        try {
            binder.commit();
            model.saveProduct();
            closeParentWindow();
        } catch (FieldGroup.CommitException e) {
            Notification.show("Error", "Валидация не прошла", Notification.Type.ERROR_MESSAGE);
        }
    }
}
