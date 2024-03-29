package com.assignment.io.assignment_3.Repository;


import com.assignment.io.assignment_3.Config.Enams.OrderStatus;
import com.assignment.io.assignment_3.Model.Entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Order findOrderByCustomerId(Long id);

    Order findOrderByCustomerIdAndStatus(Long id, OrderStatus orderStatus);

    List<Order> findAllByCustomerIdAndStatus(Long id, OrderStatus orderStatus);

    List<Order> findAllByStatus(OrderStatus orderStatus);

}
