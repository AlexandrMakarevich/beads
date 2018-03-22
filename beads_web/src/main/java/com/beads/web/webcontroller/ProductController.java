package com.beads.web.webcontroller;

import com.beads.model.dao.ProductDao;
import com.beads.model.dao.ProductGroupDao;
import com.beads.model.domain.Product;
import com.beads.web.dao.ProductDaoImpl;
import com.beads.web.dao.ProductGroupDaoImpl;
import com.beads.web.domain.ShoppingCard;
import com.beads.web.webcontroller.response.ProductPageView;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import javax.annotation.Resource;
import java.util.List;

/**
 * Created by alexey.dranchuk on 25.08.14.
 *
 */
@Controller
public class ProductController {

    public static final Logger LOG = LoggerFactory.getLogger(ProductController.class);

    @Resource(name = ProductDaoImpl.BEAN_NAME)
    private ProductDao productDao;

    @Resource(name = ProductGroupDaoImpl.BEAN_NAME)
    private ProductGroupDao productGroupDao;

    @Autowired
    private ShoppingCard shoppingCard;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getMainData(Model model) {
        ProductPageView ppv = new ProductPageView();
        ppv.setProducts(productDao.loadAllProducts());
        return buildProductViewResponse(model, ppv);
    }

    @RequestMapping(value="/searchProducts", method = RequestMethod.GET)
    public String searchProducts(Model model, String searchString) {
        ProductPageView ppv = new ProductPageView();
        List<Product> products;
        if (StringUtils.isEmpty(searchString)) {
            products = productDao.loadAllProducts();
        } else {
            products = productDao.searchProductBySearchString(searchString);
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

    @RequestMapping(value="/loadProductsByGroupId", method = RequestMethod.GET)
    public String loadProductsByGroupId(Model model, @RequestParam("groupId")int groupId) {
        List<Product> products = productDao.loadProductByGroupId(groupId);
        ProductPageView ppv = new ProductPageView();
        ppv.setProducts(products);
        return buildProductViewResponse(model, ppv);
    }


        @ModelAttribute("order")
    public ShoppingCard getShoppingCard() {
        return shoppingCard;
    }
}
