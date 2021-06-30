package com.assignment.io.assignment_3.Service;

import com.assignment.io.assignment_3.Model.DTO.OrderDTO;
import com.assignment.io.assignment_3.Model.Entity.Customer;
import com.assignment.io.assignment_3.Model.Entity.Product;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface OtherService {
    ResponseEntity<List<Product>> getAllProducts();

    ResponseEntity<Product> getProductById(Long id);

    ResponseEntity customOrder(List<OrderDTO> orderDTOList, Customer customer);

    ResponseEntity sortProductByCategory(Long id);

    ResponseEntity karzinka(OrderDTO orderDTO, Customer customer);
}
