package com.beads.vaadin.components.commons;

import com.vaadin.ui.Button;
import com.vaadin.ui.UI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by alexey.dranchuk on 26/12/14.
 *
 */
public class MenuItemClickListener implements Button.ClickListener {

    public static final Logger LOG = LoggerFactory.getLogger(MenuItemClickListener.class);

    private String viewName;

    public MenuItemClickListener(String viewName) {
        this.viewName = viewName;
    }

    @Override
    public void buttonClick(Button.ClickEvent event) {
        LOG.debug("Chossed menu item {}", viewName);
        UI.getCurrent().getNavigator().navigateTo(viewName);
    }
}
