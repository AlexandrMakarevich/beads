package com.lena.webcontroller;

import com.lena.dao.ProductGroupDao;
import com.lena.domain.Product;
import com.lena.domain.ShoppingCard;
import com.lena.service.ProductService;
import com.lena.webcontroller.response.ProductPageView;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by Administrator on 25.08.14.
 */
@Controller
public class ProductController {

    public static final Logger LOG = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductGroupDao productGroupDao;

    @Autowired
    private ShoppingCard shoppingCard;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getMainData(Model model) {
        LOG.trace("getMainData");
        ProductPageView ppv = new ProductPageView();
        ppv.setProducts(productService.findProducts());
        return buildProductViewResponse(model, ppv);
    }

    @RequestMapping(value="/searchProducts", method = RequestMethod.GET)
    public String searchProducts(Model model, String searchString) {
        ProductPageView ppv = new ProductPageView();
        List<Product> products;
        if (StringUtils.isEmpty(searchString)) {
            products = productService.findProducts();
        } else {
            products = productService.searchProductBySearchString(searchString);
        }
        ppv.setProducts(products);
        ppv.setSearchString(searchString);
        return buildProductViewResponse(model, ppv);
    }

    private String buildProductViewResponse(Model model, ProductPageView ppv) {
        ppv.setCategories(productGroupDao.findAllProductGroup());
        model.addAttribute("pv", ppv);
        return "/main";
    }

    @ModelAttribute("order")
    public ShoppingCard getShoppingCard() {
        return shoppingCard;
    }
}
