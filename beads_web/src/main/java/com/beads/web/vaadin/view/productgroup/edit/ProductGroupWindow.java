package com.beads.web.vaadin.view.productgroup.edit;

import com.vaadin.ui.Window;

/**
 * Created by alexey.dranchuk on 29/12/14.
 *
 */
public class ProductGroupWindow extends Window {

    public ProductGroupWindow(ProductGroupWindowModel model) {
        setCaption("Группа товаров");
        setContent(new ProductGroupFormLayout(model));
        setModal(true);
        setHeight(200, Unit.POINTS);
        setWidth(350, Unit.POINTS);
        setImmediate(true);
        center();
    }
}
