package com.example.sv.Service;

import com.example.sv.Repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;



    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Long getLastOrderId() {
        return orderRepository.findAll(Sort.by(Sort.Direction.DESC, "id")).get(0).getId();
    }
}
