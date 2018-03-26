package com.beads.model.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Type;

/**
 * Created by alexey.dranchuk on 28.09.14.
 *
 */
@NamedQuery(name = "loadPendingOrdersIds",
    query = "select o.id from Order o where o.status = :status order by o.id asc")
@Entity
@Table(name = "`order`")
public class Order {

    public static final String ID = "id";

    public static final String EMAIL = "email";

    public static final String ORDER_DETAILS = "orderDetails";

    public static final String STATUS = "status";

    public static final String PHONE_NUMBER = "phoneNumber";

    public static final String MODIFIED_DATE = "modifyDate";

    public static final String DELIVERY_ADDRESS = "deliveryAddress";

    public static final String ORDER_ITEMS = "orderItems";

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = ID)
    private Integer id;

    @NotNull(message = "email can't be empty or null")
    @Column(name = "email")
    private String email;

    @Column(name = "details")
    @Type(type="text")
    private String orderDetails;

    @Column(name = STATUS)
    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.PENDING;

    @Column(name="phone_number")
    private String phoneNumber;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "order_order_items",
            joinColumns = { @JoinColumn(name = "order_id") },
            inverseJoinColumns = { @JoinColumn(name = "item_id") })
    private List<OrderItem> orderItems;

    @Column(name = "modified_date", columnDefinition = "timestamp")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifyDate = LocalDateTime.now();

    @Column(name = "delivery_address")
    private String deliveryAddress;

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public Order() {
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer orderId) {
        this.id = orderId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(String orderDetails) {
        this.orderDetails = orderDetails;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public BigDecimal getTotalPrice() {
        return calculateTotalPrice();
    }

    protected BigDecimal calculateTotalPrice() {
        BigDecimal total = BigDecimal.valueOf(0);
        for (OrderItem orderItem : getOrderItems()) {
            total = total.add(orderItem.calculateCost());
        }
        return total;
    }

    public void setModifyDate(LocalDateTime modifyDate) {
        this.modifyDate = modifyDate;
    }

    public LocalDateTime getModifyDate() {
        return modifyDate;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }
}
