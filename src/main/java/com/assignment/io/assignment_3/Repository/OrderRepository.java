package com.assignment.io.assignment_3.Repository;


import com.assignment.io.assignment_3.Model.Entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

}
