package com.beads.email.service;

import com.beads.model.domain.Order;
import java.io.StringWriter;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by alexey.dranchuk on 29/1/15.
 *
 */

@Service
public class EmailGenerator {

    public static final Logger LOG = LoggerFactory.getLogger(EmailGenerator.class);

    @Autowired
    private VelocityEngine velocityEngine;

    public String getEmailBody(Order order) {
        VelocityContext context = new VelocityContext();
        context.put("order", order);
        StringWriter stringWriter = new StringWriter();
        velocityEngine.mergeTemplate("email/manager.email.vm",
            "UTF-8",
            context,
            stringWriter);
        return stringWriter.toString();
    }
}
