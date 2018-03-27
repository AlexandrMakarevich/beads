package com.beads.web.vaadin.view.productgroup;

import com.beads.model.dao.ProductGroupDao;
import com.beads.model.domain.ProductGroup;
import com.beads.web.dao.ProductGroupDaoImpl;
import com.beads.web.vaadin.view.productgroup.edit.ProductGroupWindow;
import com.beads.web.vaadin.view.productgroup.edit.ProductGroupWindowModel;
import com.beads.web.vaadin.view.productgroup.listener.ProductGroupSearchEvent;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.ui.UI;
import java.util.Collection;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * Created by alexey.dranchuk on 27/12/14.
 *
 */
@Component
@Lazy
public class ProductGroupTableModel implements ItemClickEvent.ItemClickListener,
        ProductGroupSearchEvent.ProductGroupSearchListener {

    public static final Logger LOG = LoggerFactory.getLogger(ProductGroupTableModel.class);

    private HierarchicalContainer container = new HierarchicalContainer();

    private Object[] visibleColumns = new Object[]{ProductGroup.ID, ProductGroup.NAME, ProductGroup.PARENT_PRODUCT_NAME, ProductGroup.ORDER_ID};

    @Resource(name = ProductGroupDaoImpl.BEAN_NAME)
    private ProductGroupDao productGroupDao;

    private BeanItem<ProductGroup> selectedProductGroup;

    @Autowired
    private ProductGroupWindowModel pgwm;

    @PostConstruct
    private void init() {
        container.addContainerProperty(ProductGroup.ID, Integer.class, null);
        container.addContainerProperty(ProductGroup.NAME, String.class, null);
        container.addContainerProperty(ProductGroup.PARENT_PRODUCT_NAME, String.class, null);
        container.addContainerProperty(ProductGroup.ORDER_ID, Integer.class, null);
        populateContainerWithFullList();
    }

    public void populateContainerWithFullList() {
        populateContainer(productGroupDao.findAllProductGroup());
    }

    public void populateContainer(List<ProductGroup> productGroups) {
        container.removeAllItems();
        for (ProductGroup pg : productGroups) {
            BeanItem<ProductGroup> item = new BeanItem<>(pg);
            container.addItem(item);
            container.setChildrenAllowed(item, pg.isChildrenAllowed());
            propagatePropertyValues(item);
            addChilds(pg.getChildGroups(), item);

        }
    }

    @SuppressWarnings("unchecked")
    private void propagatePropertyValues(BeanItem item) {
        for (Object propertyId : container.getContainerPropertyIds()) {
            Object value = item.getItemProperty(propertyId).getValue();
            container.getItem(item).getItemProperty(propertyId).setValue(value);
        }
    }

    private void addChilds(List<ProductGroup> childGroups, BeanItem parentGroup) {
        for (ProductGroup pg : childGroups) {
            BeanItem<ProductGroup> item = new BeanItem<>(pg);
            container.addItem(item);
            container.setChildrenAllowed(item, pg.isChildrenAllowed());
            propagatePropertyValues(item);
            container.setParent(item, parentGroup);
            if (pg.isChildrenAllowed()) {
                addChilds(pg.getChildGroups(), item);
            }

        }
    }

    public HierarchicalContainer getContainer() {
        return container;
    }

    public Object[] getVisibleColumns() {
        return visibleColumns;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void itemClick(ItemClickEvent event) {
        selectedProductGroup = (BeanItem<ProductGroup>) event.getItemId();
        LOG.trace("Selected productGroup item {}", selectedProductGroup);
        if (event.isDoubleClick()) {
            showEditProductGroup(selectedProductGroup);
        }
    }

    private void showEditProductGroup(BeanItem<ProductGroup> selectedProductGroup) {
        pgwm.setProductGroup(selectedProductGroup.getBean());
        ProductGroupWindow productGroupWindow = new ProductGroupWindow(pgwm);
        UI.getCurrent().addWindow(productGroupWindow);
    }

    public void deleteButtonClick() {
        if (selectedProductGroup != null) {
            removeProductGroupWithChilds();
            productGroupDao.deleteProductGroup(selectedProductGroup.getBean());
        }
        LOG.debug("Deleted productGroup {} with all childs", selectedProductGroup);
        selectedProductGroup = null;
    }

    private void removeProductGroupWithChilds() {
        Collection childs = container.getChildren(selectedProductGroup);
        if (childs != null) {
            for(Object childItem : childs) {
                container.removeItem(childItem);
            }
        }
        container.removeItem(selectedProductGroup);
    }

    @Override
    public void fireSearch(ProductGroupSearchEvent event) {
        List<ProductGroup> productGroups = productGroupDao.findProductGroupsByName(event.getSearchString());
        populateContainer(productGroups);
    }

    public ProductGroup getSelectedProductGroup() {
        return selectedProductGroup.getBean();
    }
}
