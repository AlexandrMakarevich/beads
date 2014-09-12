package com.lena.webcontroller;

import com.lena.domain.Order;
import com.lena.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Administrator on 25.08.14.
 */
@Controller
public class ProductController {

    public static final Logger LOG = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @Autowired
    private Order order;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getMainData(Model model) {
        LOG.trace("getMainData");
        model.addAttribute("products", productService.findProducts());
        return "/main";
    }

    @ModelAttribute("order")
    public Order getOrder() {
        return order;
    }
}
