package com.jx.mall.service;

import com.jx.mall.entity.*;
import com.jx.mall.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static com.jx.mall.entity.LogisticsRecord.INBOUND;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private LogisticsRecordRepository logisticsRepository;

    @Transactional
    public Order createOrder(List<OrderItem> orderItems) {
        Order order = Order.createOrder();
        BigDecimal totalPrice = order.getTotalPrice();
        // save order
        order = orderRepository.save(order);

        //check inventory and save orderItem
        for (OrderItem orderItem : orderItems) {
            //find product
            Product product = productRepository.getOne(orderItem.getProductId());
            Optional<Inventory> inventoryOptional = inventoryRepository.findById(product.getId());
            if (!inventoryOptional.isPresent()) {
                return null;
            }

            Inventory inventory = inventoryOptional.get();
            if (inventory.getCount() < orderItem.getPurchaseCount()) {
                return null;
            }

            //更新库存信息
            inventory.lockCount(orderItem.getPurchaseCount());

            //保存OrderItem
            orderItemRepository.save(new OrderItem(order.getId(),
                            orderItem.getProductId(),
                            orderItem.getPurchaseCount(),
                            product.getName(),
                            product.getDescription(),
                            product.getPrice()
                    )
            );
            totalPrice = totalPrice.add(product.getPrice().multiply(new BigDecimal(orderItem.getPurchaseCount())));
        }
        order = orderRepository.save(order);
        return order;
    }


    @Transactional
    public void pay(Long orderId) {
        //save logistics
        LogisticsRecord logisticsRecord = new LogisticsRecord(null, INBOUND, null, null);
        logisticsRecord = logisticsRepository.save(logisticsRecord);

        //set order logistics
        Order order = orderRepository.getOne(orderId);
        order.pay(logisticsRecord.getId());
        //save order
        orderRepository.save(order);
    }

    @Transactional
    public void withdrawn(Long orderId) {
        //set order status
        Order order = orderRepository.getOne(orderId);
        order.withdrawn();
        //save order
        orderRepository.save(order);

        unlockInventory(order);
    }

    private void unlockInventory(Order order) {
        for (OrderItem orderItem : order.getPurchaseItemList()) {
            Optional<Inventory> inventoryOptional = inventoryRepository.findById(orderItem.getProductId());
            if (inventoryOptional.isPresent())
                inventoryOptional.get().unlockCount(orderItem.getPurchaseCount());
        }
    }


}
