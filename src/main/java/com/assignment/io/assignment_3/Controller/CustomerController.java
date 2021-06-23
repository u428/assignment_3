package com.assignment.io.assignment_3.Controller;

import com.assignment.io.assignment_3.Model.DTO.OrderDTO;
import com.assignment.io.assignment_3.Model.Entity.Customer;
import com.assignment.io.assignment_3.Model.Entity.Product;
import com.assignment.io.assignment_3.Security.CurrentUser;
import com.assignment.io.assignment_3.Service.OtherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/customer")
public class CustomerController {

    @Autowired
    private OtherService otherService;

    @PostMapping(path = "/customOrder")
    public ResponseEntity customOrder(@RequestBody List<OrderDTO> orderDTOList, @CurrentUser Customer customer){
        return otherService.customOrder(orderDTOList, customer);
    }

    @GetMapping(path = "/getproducts")
    public ResponseEntity<List<Product>> getAllProduct(){
        return otherService.getAllProducts();
    }

    @GetMapping(path = "/getproduct/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id){
        return otherService.getProductById(id);
    }

    @GetMapping(path = "/sortProductByCategory/{id}")
    public ResponseEntity sortProductByCategory(@PathVariable Long id){
        return otherService.sortProductByCategory(id);
    }

}
