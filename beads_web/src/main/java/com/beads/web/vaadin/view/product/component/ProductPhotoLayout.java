package com.beads.web.vaadin.view.product.component;

import com.vaadin.server.ExternalResource;
import com.vaadin.ui.Image;
import com.vaadin.ui.Upload;
import com.vaadin.ui.VerticalLayout;

/**
 * Created by alexey.dranchuk on 10/1/15.
 *
 */
public class ProductPhotoLayout extends VerticalLayout {

    private ProductWindowModel model;

    private Image productPhoto;

    private Upload upload;

    public ProductPhotoLayout(ProductWindowModel model) {
        this.model = model;
        productPhoto = new Image("Фото Продукта", new ExternalResource("getPhoto?id=" + model.getProduct().getId()));
        addUpload();
        addImage();
    }

    private void addUpload() {
        PhotoReciever photoReciever = new PhotoReciever(productPhoto, model);
        upload = new Upload("Загрузить фото", photoReciever);
        upload.setButtonCaption("Загрузить");
        upload.addSucceededListener(photoReciever);
        addComponent(upload);
    }

    private void addImage() {
        productPhoto.setWidth(300, Unit.POINTS);
        productPhoto.setHeight(300, Unit.POINTS);
        addComponent(productPhoto);
    }
}
