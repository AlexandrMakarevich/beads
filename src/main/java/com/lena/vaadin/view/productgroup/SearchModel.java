package com.lena.vaadin.view.productgroup;


import com.lena.dao.ProductGroupDao;
import com.lena.dao.ProductGroupDaoImpl;
import com.lena.vaadin.SpringContextHelper;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.server.VaadinServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by alexey.dranchuk on 26/12/14.
 */
public class SearchModel {

    public static final Logger LOG = LoggerFactory.getLogger(SearchModel.class);

    private ObjectProperty<String> searchProperty = new ObjectProperty<String>("");

    public ObjectProperty<String> getSearchProperty() {
        return searchProperty;
    }

    public Object getSearchPropertyValue() {
        return searchProperty.getValue();
    }

    public void processClickEvent() {
        LOG.debug("search value : {}", getSearchPropertyValue());
    }

}
