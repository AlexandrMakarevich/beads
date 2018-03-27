package com.beads.web.vaadin.listener;

import com.beads.web.vaadin.view.order.listener.OrderChangeEvent;
import com.beads.web.vaadin.view.order.listener.OrderSearchEvent;
import com.beads.web.vaadin.view.product.listener.ProductChangeEvent;
import com.beads.web.vaadin.view.product.listener.ProductSearchEvent;
import com.beads.web.vaadin.view.productgroup.listener.ProductGroupSearchEvent;
import com.github.wolfie.blackboard.Blackboard;
import com.github.wolfie.blackboard.Event;
import com.github.wolfie.blackboard.Listener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by alexey.dranchuk on 8/1/15.
 *
 */
@Component
public class EventBus {

    public static final Logger LOG = LoggerFactory.getLogger(EventBus.class);

    private Blackboard blackboard = new Blackboard();

    public EventBus() {
        initEventBus();
    }

    public void initEventBus() {
        if (LOG.isDebugEnabled()) {
            blackboard.enableLogging();
        }
        blackboard.register(ProductGroupSearchEvent.ProductGroupSearchListener.class, ProductGroupSearchEvent.class);
        blackboard.register(ProductSearchEvent.ProductSearchListener.class, ProductSearchEvent.class);
        blackboard.register(ProductChangeEvent.ProductChangeListner.class, ProductChangeEvent.class);
        blackboard.register(OrderChangeEvent.OrderChangeListener.class, OrderChangeEvent.class);
        blackboard.register(OrderSearchEvent.OrderSearchListener.class, OrderSearchEvent.class);
    }

    public void addListener(Listener listener) {
        blackboard.addListener(listener);
    }

    public void fireEvent(Event event) {
        blackboard.fire(event);
    }
}
