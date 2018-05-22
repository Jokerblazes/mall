package com.jx.mall.repository;

import com.jx.mall.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long>{
    public List<Order> findAllByUserId(Long userId);
}
