package com.assignment.io.assignment_3.Repository;

import com.assignment.io.assignment_3.Model.Entity.Payment;
import com.assignment.io.assignment_3.Model.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}