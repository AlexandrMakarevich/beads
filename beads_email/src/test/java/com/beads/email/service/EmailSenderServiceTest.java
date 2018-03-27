package com.beads.email.service;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import com.beads.email.dao.OrderDaoImpl;
import com.beads.email.util.Batch;
import com.beads.model.builder.OrderBuilder;
import com.beads.model.domain.Order;
import com.beads.model.domain.OrderStatus;
import java.util.Arrays;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class EmailSenderServiceTest {

    private EmailSender emailSenderMock = mock(EmailSender.class);
    private OrderDaoImpl orderDaoMock = mock(OrderDaoImpl.class);
    private EmailSenderService emailSenderService;
    private OrderBuilder orderBuilder;

    @Before
    public void init() {
        emailSenderService = new EmailSenderService(emailSenderMock, orderDaoMock);
        orderBuilder = new OrderBuilder();
    }

    /**
     * This test checks the functionality of EmailSenderService.
     * For this we need mocks EmailSender & OrderDaoImpl.
     */
    @Test
    public void testSendEmailWithOrderStatusPending()  {
        Order order = orderBuilder.withId(1).build();
        when(emailSenderMock.sendEmail(any(Order.class))).thenReturn(false);
        when(orderDaoMock.loadOrderById(order.getId())).thenReturn(order);

        Batch testBatch = new Batch(Arrays.asList(order.getId()));
        emailSenderService.sendEmail(testBatch);

        Mockito.verify(emailSenderMock, Mockito.times(1)).sendEmail(order);
        Mockito.verify(orderDaoMock, Mockito.times(1)).saveOrUpdate(order);
        
        Assert.assertEquals("Order status must be changed on ERROR",
            order.getStatus(), OrderStatus.ERROR);
    }
}
