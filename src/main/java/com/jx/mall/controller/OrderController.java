package com.jx.mall.controller;

import com.jx.mall.entity.Order;
import com.jx.mall.entity.OrderItem;
import com.jx.mall.repository.OrderRepository;
import com.jx.mall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.jx.mall.entity.Order.PAID;
import static com.jx.mall.entity.Order.WITHDRAWN;

@RestController
@RequestMapping(value = "/orders")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderService orderService;

    @Value("${app.host}")
    private String host;

    @Value("${app.port}")
    private String port;


    /**
     * get a order by id
     * @param id
     * @return
     */
    @GetMapping(value = "/{id}")
    public Order getOrderById(@PathVariable Long id) {
        Optional<Order> orderOptional = orderRepository.findById(id);
        return orderOptional.isPresent() ? orderOptional.get() : null;
    }

    /**
     * update order status
     *
     * @param id order id
     * @param orderStatus new status
     * @return
     */
    @PutMapping(value = "/{id}")
    public ResponseEntity<String> updateOrdersStatus(@PathVariable Long id, @RequestParam String orderStatus) {
        Optional<Order> orderOptional = orderRepository.findById(id);
        if (orderOptional.isPresent()) {
            if (PAID.equals(orderStatus))
                orderService.pay(id);
            else if (WITHDRAWN.equals(orderStatus))
                orderService.withdrawn(id);
            else
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     *
     * @param orderItems
     * @return
     */
    @PostMapping()
    public ResponseEntity<String> addOrder(@RequestBody List<OrderItem> orderItems) {
        Order order = orderService.createOrder(orderItems);
        if (order == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        HttpHeaders headers = new HttpHeaders();

        headers.add("location", String.format("http://%s:%s/orders/%d", host, port, order.getId()));
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    /**
     * get orders by userId
     * @param userId
     * @return
     */
    @GetMapping
    public List<Order> getOrdersByUserId(@RequestParam Long userId) {
        return orderRepository.findAllByUserId(userId);
    }
}
