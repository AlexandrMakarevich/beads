package com.beads.email.service;

import com.beads.email.config.EmailSenderConfig;
import com.beads.model.builder.OrderBuilder;
import com.beads.model.domain.Order;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

/**
 * Created by alexey.dranchuk on 31/1/15.
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={EmailGenerator.class, EmailSenderConfig.class}, loader=AnnotationConfigContextLoader.class)
public class EmailGeneratorTest {

    @Autowired
    private EmailGenerator emailGenerator;

    private OrderBuilder orderBuilder;

    @Before
    public void init() {
        orderBuilder = new OrderBuilder();
    }

    @Test
    public void test() {
        Order order = orderBuilder.withOrderItems(orderBuilder.buildOrderItems()).build();
        String actualMessage = emailGenerator.getEmailBody(order);
    }
}
