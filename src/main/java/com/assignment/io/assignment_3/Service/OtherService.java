package com.assignment.io.assignment_3.Service;

import com.assignment.io.assignment_3.Model.DTO.OrderDTO;
import com.assignment.io.assignment_3.Model.Entity.Customer;
import com.assignment.io.assignment_3.Model.Entity.Product;
import org.springframework.http.ResponseEntity;

import java.net.MalformedURLException;
import java.util.List;

public interface OtherService {
    ResponseEntity getAllProducts(int page, int limit);

    ResponseEntity<Product> getProductById(Long id);

    ResponseEntity customOrder(List<OrderDTO> orderDTOList, Customer customer);

    ResponseEntity sortProductByCategory(Long id, int limit, int page);

    ResponseEntity karzinka(OrderDTO orderDTO, String userName);

    ResponseEntity getKarzinka(String telNomer);

    ResponseEntity image(Long id) throws MalformedURLException;

    ResponseEntity getAllcategory();
}
