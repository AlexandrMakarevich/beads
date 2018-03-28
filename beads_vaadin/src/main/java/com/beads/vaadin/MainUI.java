package com.beads.vaadin;

import com.beads.vaadin.components.MenuPanel;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.UI;
import org.springframework.beans.factory.annotation.Autowired;

@SpringUI
public class MainUI extends UI {

  @Autowired
  private SpringViewProvider springViewProvider;

  @Override
  protected void init(VaadinRequest request) {
    HorizontalSplitPanel splitPanel = new HorizontalSplitPanel();
    splitPanel.setFirstComponent(new MenuPanel());
    splitPanel.setSplitPosition(15, Unit.PERCENTAGE);
    ContextPanel cp = new ContextPanel();
    splitPanel.setSecondComponent(cp);
    splitPanel.setSizeFull();
    initNavigator(cp);
    setContent(splitPanel);
  }

  private void initNavigator(ContextPanel cp) {
    Navigator navigator = new Navigator(this, cp);
    navigator.addProvider(springViewProvider);
  }
}
