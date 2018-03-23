package com.beads.email.service;

import com.beads.model.domain.Order;
import java.io.StringWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

/**
 * Created by alexey.dranchuk on 29/1/15.
 *
 */

@Service
public class EmailGenerator {

    public static final Logger LOG = LoggerFactory.getLogger(EmailGenerator.class);
    public static final String EMAIL_TEMPLATE = "manager.email";
    public static final String MODEL_NAME = "order";

    @Autowired
    private SpringTemplateEngine templateEngine;

    public String getEmailBody(Order order) {
        Context context = new Context();
        context.setVariable(MODEL_NAME, order);
        StringWriter stringWriter = new StringWriter();
        templateEngine.process(EMAIL_TEMPLATE, context, stringWriter);
        return stringWriter.toString();
    }
}
