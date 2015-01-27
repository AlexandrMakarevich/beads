package com.lena.vaadin.components;

import com.lena.vaadin.components.common.MenuItemClickListener;
import com.lena.vaadin.view.product.ProductView;
import com.lena.vaadin.view.productgroup.ProductGroupView;
import com.vaadin.ui.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Created by alexey.dranchuk on 26/12/14.
 */
public class MenuPanel extends VerticalLayout {

    public static final Logger LOG = LoggerFactory.getLogger(MenuPanel.class);


    public MenuPanel() {
        setSpacing(true);
        setMargin(true);
        setImmediate(true);
        initComponents();
    }

    private void initComponents() {
        addLoginInfo();
        addComponent(new Button("Группы продуктов", new MenuItemClickListener(ProductGroupView.VIEW_NAME)));
        addComponent(new Button("Товар", new MenuItemClickListener(ProductView.VIEW_NAME)));
        addComponent(new Button("Заказы"));
        addComponent(new Button("Выход", new LogoutClickListener()));
    }

    private void addLoginInfo() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName(); //get logged in username
        Label loginLabel = new Label("login : " + name);
        addComponent(loginLabel);
    }
}
