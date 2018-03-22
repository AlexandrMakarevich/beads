package com.beads.web.vaadin.view.product.component;

import static com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.beads.model.dao.ProductDao;
import com.beads.model.domain.Product;
import com.beads.web.vaadin.listener.EventBus;
import com.beads.web.vaadin.view.product.listener.ProductChangeEvent;
import com.beads.web.vaadin.view.product.listener.ProductSearchEvent;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.ui.UI;
import java.util.List;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by alexey.dranchuk on 8/1/15.
 *
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Lazy
public class ProductTableModel extends BeanItemContainer<Product>
        implements ItemClickListener, ProductSearchEvent.ProductSearchListener, ProductChangeEvent.ProductChangeListner {

    public static final Logger LOG = LoggerFactory.getLogger(ProductTableModel.class);

    @Autowired
    private ProductDao productDao;

    @Autowired
    private EventBus eventBus;

    @Autowired
    private ProductWindowModel productWindowModel;

    private Object[] visibleColumns = new Object[]{
            Product.ID, Product.ARTIKUL, Product.PRICE, Product.NAME, Product.GROUP_NAME, Product.DESCRIPTION};

    private Product selectedProduct;

    public ProductTableModel() {
        super(Product.class);
    }

    @PostConstruct
    private void init() {
        addNestedContainerProperty(Product.ID);
        addNestedContainerProperty(Product.NAME);
        addNestedContainerProperty(Product.ARTIKUL);
        addNestedContainerProperty(Product.GROUP_NAME);
        addNestedContainerProperty(Product.DESCRIPTION);
        populateContainerFullList();
        eventBus.addListener(this);
    }

    private void populateContainerFullList() {
        populateContainer(productDao.loadAllProducts());
    }

    private void populateContainer(List<Product> products) {
        removeAllItems();
        addAll(products);
    }


    public Object[] getVisibleColumns() {
        return visibleColumns;
    }

    @Override
    @SuppressWarnings("unchecked")
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
