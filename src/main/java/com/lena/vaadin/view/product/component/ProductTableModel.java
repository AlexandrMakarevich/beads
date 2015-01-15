package com.lena.vaadin.view.product.component;

import com.lena.dao.ProductDao;
import com.lena.domain.Product;
import com.lena.vaadin.listener.EventBus;
import com.lena.vaadin.view.product.listener.ProductChangeEvent;
import com.lena.vaadin.view.product.listener.ProductSearchEvent;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.ui.UI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.util.List;
import static com.lena.vaadin.view.product.listener.ProductSearchEvent.*;
import static com.vaadin.event.ItemClickEvent.*;

/**
 * Created by alexey.dranchuk on 8/1/15.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Lazy
public class ProductTableModel extends BeanItemContainer<Product>
        implements ItemClickListener, ProductSearchListener, ProductChangeEvent.ProductChangeListner {

    public static final Logger LOG = LoggerFactory.getLogger(ProductTableModel.class);

    @Autowired
    private ProductDao productDao;

    @Autowired
    private EventBus eventBus;

    @Autowired
    private ProductWindowModel productWindowModel;

    private Object[] visibleColumns = new Object[]{
            Product.ID, Product.PRODUCT_CODE, Product.PRICE, Product.NAME, Product.GROUP_NAME, Product.DESCRIPTION};

    private Product selectedProduct;

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
        selectedProduct = ((BeanItem<Product>) event.getItem()).getBean();
        if (event.isDoubleClick()) {
            showEditProduct(selectedProduct);
        }
    }

    private void showEditProduct(Product selectedProduct) {
        productWindowModel.setProduct(selectedProduct);
        LOG.debug("Edit product {}", productWindowModel.getProduct().toString());
        ProductWindow productWindow = new ProductWindow(productWindowModel);
        UI.getCurrent().addWindow(productWindow);
    }

    @Override
    public void fireSearch(ProductSearchEvent event) {
        LOG.debug("Trying to search with pattern {}", event.getSearchString());
        populateContainer(productDao.searchProductBySearchString(event.getSearchString()));
    }

    @Override
    public void fireProductChange(ProductChangeEvent event) {
        LOG.debug("Product {} was updated", event.getProduct());
        removeItem(event.getProduct());
        addItemAt(0, event.getProduct());
    }

    public void deleteSelectedProduct() {
        if (selectedProduct != null) {
            LOG.debug("Trying to delete product {}", selectedProduct);
            removeItem(selectedProduct);
            productDao.removeProduct(selectedProduct);
            selectedProduct = null;
        }
    }

}
