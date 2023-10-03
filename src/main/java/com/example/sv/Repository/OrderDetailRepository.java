package com.example.sv.Repository;

import com.example.sv.Model.Order;
import com.example.sv.Model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailRepository  extends JpaRepository<OrderDetail, Long> {

}
