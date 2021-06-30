package com.assignment.io.assignment_3.Service;

import com.assignment.io.assignment_3.Model.DTO.SetProductDto;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface AdminService {
    ResponseEntity allOrders();

    ResponseEntity allPayments();

    ResponseEntity setProduct(SetProductDto setProductDto) throws IOException;

    ResponseEntity addCategort(String categoryName);
}
