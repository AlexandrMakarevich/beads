package com.lena.vaadin.view.product.component;

import com.lena.dao.ProductDao;
import com.lena.domain.Product;
import com.lena.vaadin.listener.EventBus;
import com.lena.vaadin.view.product.listener.ProductSearchEvent;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ItemClickEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Created by alexey.dranchuk on 8/1/15.
 */
@Component
public class ProductTableModel extends BeanItemContainer<Product>
        implements ItemClickEvent.ItemClickListener, ProductSearchEvent.ProductSearchListener {

    public static final Logger LOG = LoggerFactory.getLogger(ProductTableModel.class);

    @Autowired
    private ProductDao productDao;

    @Autowired
    private EventBus eventBus;

    private Object[] visibleColumns = new Object[]{
            Product.ID, Product.PRODUCT_CODE, Product.PRICE, Product.NAME, Product.GROUP_NAME, Product.DESCRIPTION};

    private BeanItem<Product> selectedProduct;

    public ProductTableModel() {
        super(Product.class);
    }

    @PostConstruct
    private void init() {
        addNestedContainerProperty(Product.ID);
        addNestedContainerProperty(Product.NAME);
        addNestedContainerProperty(Product.PRODUCT_CODE);
        addNestedContainerProperty(Product.GROUP_NAME);
        addNestedContainerProperty(Product.DESCRIPTION);
        populateContainerFullList();
        eventBus.addListener(this);
    }

    private void populateContainerFullList() {
        populateContainer(productDao.findAllProducts());
    }

    private void populateContainer(List<Product> products) {
        removeAllItems();
        addAll(products);
    }


    public Object[] getVisibleColumns() {
        return visibleColumns;
    }

    @Override
    public void itemClick(ItemClickEvent event) {
        selectedProduct = (BeanItem<Product>) event.getItem();
        LOG.trace("Selected product {}", selectedProduct.getBean().toString());
    }

    @Override
    public void fireSearch(ProductSearchEvent event) {
        populateContainer(productDao.searchProductBySearchString(event.getSearchString()));
    }
}
