package com.beads.email.service;

import com.beads.email.config.EmailSenderConfig;
import com.beads.model.builder.OrderBuilder;
import com.beads.model.domain.Order;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.IfProfileValue;
import org.springframework.test.annotation.ProfileValueSourceConfiguration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={EmailGenerator.class, EmailSender.class, EmailSenderConfig.class},
        loader=AnnotationConfigContextLoader.class)
@ProfileValueSourceConfiguration
@TestPropertySource(properties = {"env=test"})
@IfProfileValue(name="test-groups", values="integration-tests")
public class EmailSenderTest {

    @Autowired
    private EmailSender emailSender;

    private OrderBuilder orderBuilder;

    @Before
    public void setup() {
        orderBuilder = new OrderBuilder();
    }

    @Test
    public void testSendEmail() throws Exception {
        Order order = orderBuilder.build();
        emailSender.sendEmail(order);
    }
}