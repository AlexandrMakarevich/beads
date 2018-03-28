package com.beads.vaadin;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

@SpringView(name = "")
public class ContextPanel extends Panel implements View {

    private Layout layout;

    public ContextPanel() {
        setSizeFull();
        initLayout();
        addComponents();
    }

    private void addComponents() {
        layout.addComponent(new Label("context panel"));
    }

    private void initLayout() {
        layout = new VerticalLayout();
        setContent(layout);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }
}
