package com.jx.mall.service;

import com.jx.mall.entity.Inventory;
import com.jx.mall.entity.LogisticsRecord;
import com.jx.mall.entity.Order;
import com.jx.mall.entity.OrderItem;
import com.jx.mall.repository.InventoryRepository;
import com.jx.mall.repository.LogisticsRecordRepository;
import com.jx.mall.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class LogisticsRecordService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private LogisticsRecordRepository logisticsRecordRepository;

    @Autowired
    private InventoryRepository inventoryRepository;


    @Transactional
    public void signed(Long logisticsId, Long orderId) {
        setLogisticsRecordSignedTimeAndStatusToSigned(logisticsId);
        setOrderFinishTimeAndStatusToFinish(orderId);

        //删除库存锁
        removeLockCount(orderId);
    }

    @Transactional
    public void removeLockCount(Long orderId) {
        for (OrderItem orderItem : orderRepository.getOne(orderId).getPurchaseItemList()) {
            Inventory inventory = inventoryRepository.getOne(orderItem.getProductId());
            inventory.releaseLockCount(orderItem.getPurchaseCount());
            inventoryRepository.save(inventory);
        }
    }

    private void setOrderFinishTimeAndStatusToFinish(Long orderId) {
        Order order = orderRepository.getOne(orderId);
        order.finish();
        orderRepository.save(order);
    }

    private void setLogisticsRecordSignedTimeAndStatusToSigned(Long logisticsId) {
        LogisticsRecord logisticsRecord = logisticsRecordRepository.getOne(logisticsId);
        logisticsRecord.signed();
        logisticsRecordRepository.save(logisticsRecord);
    }

}
