package com.lena.domain;

import javax.persistence.*;
import java.sql.Blob;

/**
 * Created by Administrator on 31.10.14.
 */
@Entity
@Table(name = "product_photo")
public class ProductPhoto {

    @Id
    @Column(name = "product_id")
    private Integer productId;

    @Lob
    @Column(name = "photo")
    private byte[] photo;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }
}
