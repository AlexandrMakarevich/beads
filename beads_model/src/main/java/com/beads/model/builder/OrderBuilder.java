package com.beads.model.builder;

import com.beads.model.domain.Order;
import com.beads.model.domain.OrderItem;
import com.beads.model.domain.OrderStatus;
import org.joda.time.DateTime;
import java.util.Arrays;
import java.util.List;

/**
 * Created by alexey.dranchuk on 29/1/15.
 *
 */

public class OrderBuilder {

    private Order order;

    private OrderItemBuilder orderItemBuilder = new OrderItemBuilder();

    public OrderBuilder() {
        initDefault();
    }

    private void initDefault() {
        order = new Order();
//        withId(1);
        withEmail("fakeEmail@dot.com");
        withOrderDetails("orderDetails");
        withStatus(OrderStatus.PENDING);
        withPhoneNumber("1234567890");
        withModifiedDate(DateTime.now());
        withOrderItems(buildOrderItems());
    }

    private OrderBuilder withModifiedDate(DateTime modifiedDate) {
        order.setModifyDate(modifiedDate);
        return this;
    }

    public List<OrderItem> buildOrderItems() {
        return Arrays.asList(orderItemBuilder.build());
    }

    public OrderBuilder withOrderItems(List<OrderItem> orderItems) {
        order.setOrderItems(orderItems);
        return this;
    }

    public OrderBuilder withPhoneNumber(String phoneNumber) {
        order.setPhoneNumber(phoneNumber);
        return this;
    }

    public OrderBuilder withStatus(OrderStatus status) {
        order.setStatus(status);
        return this;
    }

    public OrderBuilder withOrderDetails(String orderDetails) {
        order.setOrderDetails(orderDetails);
        return this;
    }

    public OrderBuilder withEmail(String email) {
        order.setEmail(email);
        return this;
    }

    public OrderBuilder withId(int id) {
        order.setId(id);
        return this;
    }

    public Order build() {
        return order;
    }

    public void reset() {
        initDefault();
    }
}
