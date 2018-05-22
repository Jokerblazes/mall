package com.jx.mall.entity;

import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @Author Joker
 * @Description
 * @Date Create in 上午8:23 2018/5/22
 */
@Entity
public class Inventory {
    @Id
    private Long productId;

    private Long count;

    private Long countLock;

    public Inventory() {
    }

    public Inventory(Long count, Long countLock) {
        this.count = count;
        this.countLock = countLock;
    }

    public Long getCountLock() {
        return countLock;
    }

    public void setCountLock(Long countLock) {
        this.countLock = countLock;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    @Transactional
    public void lockCount(long num) {
        countLock = countLock + num;
        count = count - num;
    }

    @Transactional
    public void unlockCount(long num) {
        countLock = countLock - num;
        count = count + num;
    }

    @Transactional
    public void releaseLockCount(long num) {
        countLock = countLock - num;
    }

    @Override
    public String toString() {
        return "Inventory{" +
                "productId=" + productId +
                ", count=" + count +
                ", countLock=" + countLock +
                '}';
    }
}
