package com.lena.vaadin.listener;

import com.github.wolfie.blackboard.Blackboard;
import com.github.wolfie.blackboard.Event;
import com.github.wolfie.blackboard.Listener;
import com.lena.vaadin.view.product.listener.ProductSearchEvent;
import com.lena.vaadin.view.productgroup.listener.ProductGroupSearchEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by alexey.dranchuk on 8/1/15.
 */
@Component
public class EventBus {

    public static final Logger LOG = LoggerFactory.getLogger(EventBus.class);

    private Blackboard blackboard = new Blackboard();

    public EventBus() {
        initEventBus();
    }

    public void initEventBus() {
        if (LOG.isDebugEnabled())
            blackboard.enableLogging();
        blackboard.register(ProductGroupSearchEvent.ProductGroupSearchListener.class, ProductGroupSearchEvent.class);
        blackboard.register(ProductSearchEvent.ProductSearchListener.class, ProductSearchEvent.class);
    }

    public void addListener(Listener listener) {
        blackboard.addListener(listener);
    }

    public void fireEvent(Event event) {
        blackboard.fire(event);
    }
}
