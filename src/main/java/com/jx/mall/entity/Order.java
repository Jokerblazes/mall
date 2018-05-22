package com.jx.mall.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author Joker
 * @Description
 * @Date Create in 上午8:24 2018/5/22
 */
@Entity
@Table(name = "`Order`") // order是mysql的关键字，故加上`
public class Order {
    public final static String PAID = "paid";
    public final static String WITHDRAWN = "withdrawn";
    public final static String UNPAID = "unpaid";
    private static final String FINISH = "finish";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String status;

    private Timestamp createTime;

    private Timestamp payTime;

    private Long logisticsId;

    private Long userId;

    private Timestamp cancelTime;

    private Timestamp finishTime;

    private BigDecimal totalPrice;

    @OneToMany(cascade = {CascadeType.ALL})
    @JoinColumn(name = "orderId")
    private List<OrderItem> purchaseItemList = new ArrayList<>();


    public static Order createOrder() {
        Order order = new Order();
        order.setCreateTime(new Timestamp(System.currentTimeMillis()));
        order.setStatus(UNPAID);
        order.setUserId(1L);
        order.setTotalPrice(new BigDecimal(0));
        return order;
    }

    public Timestamp getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Timestamp finishTime) {
        this.finishTime = finishTime;
    }

    public List<OrderItem> getPurchaseItemList() {
        return purchaseItemList;
    }


    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Order() {
    }

    public Order(String status, Timestamp createTime, Timestamp payTime, Long logisticsId, Long userId, Timestamp cancelTime, Timestamp finishTime, BigDecimal totalPrice) {
        this.status = status;
        this.createTime = createTime;
        this.payTime = payTime;
        this.logisticsId = logisticsId;
        this.userId = userId;
        this.cancelTime = cancelTime;
        this.finishTime = finishTime;
        this.totalPrice = totalPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getPayTime() {
        return payTime;
    }

    public void setPayTime(Timestamp payTime) {
        this.payTime = payTime;
    }

    public Long getLogisticsId() {
        return logisticsId;
    }

    public void setLogisticsId(Long logisticsId) {
        this.logisticsId = logisticsId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Timestamp getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(Timestamp cancelTime) {
        this.cancelTime = cancelTime;
    }

    public void pay(Long logisticsId) {
        this.status = PAID;
        this.payTime = new Timestamp(System.currentTimeMillis());
        this.logisticsId = logisticsId;
    }

    public void withdrawn() {
        this.status = WITHDRAWN;
        this.cancelTime = new Timestamp(System.currentTimeMillis());
    }

    public void finish() {
        this.status = FINISH;
        finishTime = new Timestamp(System.currentTimeMillis());
    }


    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", status='" + status + '\'' +
                ", createTime=" + createTime +
                ", payTime=" + payTime +
                ", logisticsId=" + logisticsId +
                ", userId=" + userId +
                ", cancelTime=" + cancelTime +
                ", finishTime=" + finishTime +
                ", totalPrice=" + totalPrice +
                ", purchaseItemList=" + purchaseItemList +
                '}';
    }
}
