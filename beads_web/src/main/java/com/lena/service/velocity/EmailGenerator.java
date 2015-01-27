package com.lena.service.velocity;

import com.lena.domain.Order;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 11.11.14.
 */
@Service
public class EmailGenerator {

    public static final Logger LOG = LoggerFactory.getLogger(EmailGenerator.class);

    @Autowired
    private VelocityEngine velocityEngine;

    public String getEmailBody(Order order) {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("order", order);
        return VelocityEngineUtils.mergeTemplateIntoString(
                velocityEngine, "email/manager.email.vm", "UTF-8", model);
    }

}
