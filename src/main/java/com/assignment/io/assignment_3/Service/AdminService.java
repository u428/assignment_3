package com.assignment.io.assignment_3.Service;

import com.assignment.io.assignment_3.Model.DTO.SetProductDto;
import org.springframework.http.ResponseEntity;

public interface AdminService {
    ResponseEntity allOrders();

    ResponseEntity getRoles();

    ResponseEntity createRole(String name);

    ResponseEntity allPayments();

    ResponseEntity setProduct(SetProductDto setProductDto);
}
