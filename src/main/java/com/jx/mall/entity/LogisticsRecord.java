package com.jx.mall.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;

/**
 * @Author Joker
 * @Description
 * @Date Create in 上午9:54 2018/5/22
 */
@Entity
public class LogisticsRecord {
    public static String INBOUND = "inbound";
    public static final String SIGNED = "signed";
    public static final String SHIPPING = "shipping";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Timestamp outboundTime;

    private String status;

    private Timestamp signedTime;

    private String deliveryMan;

    public LogisticsRecord() {
    }

    public LogisticsRecord(Timestamp outboundTime, String status, Timestamp signedTime, String deliveryMan) {
        this.outboundTime = outboundTime;
        this.status = status;
        this.signedTime = signedTime;
        this.deliveryMan = deliveryMan;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getOutboundTime() {
        return outboundTime;
    }

    public void setOutboundTime(Timestamp outboundTime) {
        this.outboundTime = outboundTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getSignedTime() {
        return signedTime;
    }

    public void setSignedTime(Timestamp signedTime) {
        this.signedTime = signedTime;
    }

    public String getDeliveryMan() {
        return deliveryMan;
    }

    public void setDeliveryMan(String deliveryMan) {
        this.deliveryMan = deliveryMan;
    }

    public void signed() {
        this.status = SIGNED;
        this.signedTime = new Timestamp(System.currentTimeMillis());
    }

    public void shipping() {
        this.status = SHIPPING;
        this.outboundTime = new Timestamp(System.currentTimeMillis());
    }


    @Override
    public String toString() {
        return "Logistics{" +
                "id=" + id +
                ", outboundTime=" + outboundTime +
                ", status='" + status + '\'' +
                ", signedTime=" + signedTime +
                ", deliveryMan='" + deliveryMan + '\'' +
                '}';
    }
}

