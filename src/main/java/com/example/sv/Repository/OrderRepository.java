package com.example.sv.Repository;

import com.example.sv.Model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT MAX(o.id) FROM Order o")
    Long findLastOrderId();


    List<Order> findByUserId(long userId);

}
