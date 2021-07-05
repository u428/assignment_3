package com.assignment.io.assignment_3.Controller;

import com.assignment.io.assignment_3.Model.DTO.OrderDTO;
import com.assignment.io.assignment_3.Model.Entity.Customer;
import com.assignment.io.assignment_3.Security.CurrentUser;
import com.assignment.io.assignment_3.Service.CustomerService;
import com.assignment.io.assignment_3.Service.OtherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/customer")
@PreAuthorize("hasRole('ROLE_USER')")
public class CustomerController {

    @Autowired
    private OtherService otherService;

    @Autowired
    private CustomerService customerService;

    @PostMapping(path = "/customOrder")
    public ResponseEntity customOrder(@RequestBody List<OrderDTO> orderDTOList, @CurrentUser Customer customer){
        return otherService.customOrder(orderDTOList, customer);
    }

    @PostMapping(path = "/addKarzinka")
    public ResponseEntity addKarzinka(@RequestBody OrderDTO orderDTO, @CurrentUser String userName){
        return otherService.karzinka(orderDTO, userName);
    }
    @GetMapping(path = "/getKarzinka")
    public ResponseEntity getKarzinka(@CurrentUser String telNomer){
        return otherService.getKarzinka(telNomer);
    }

    @GetMapping(path = "/getCurrentCustomer")
    public ResponseEntity getCurrentCustomer(@CurrentUser String telNomer){
        return customerService.getCurrentCustomer(telNomer);
    }

    @PostMapping(path = "/buy")
    public ResponseEntity buy(@RequestParam double summa, @CurrentUser String telNomer){
        return customerService.buy(summa, telNomer);
    }

    @GetMapping(path="/payment")
    public ResponseEntity payment(@CurrentUser String telNomer){
        return customerService.payment(telNomer);
    }



}
