package com.assignment.io.assignment_3.Controller;

import com.assignment.io.assignment_3.Model.DTO.SetProductDto;
import com.assignment.io.assignment_3.Service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping(path = "/allPayments")
    public ResponseEntity allPayments(){
        return adminService.allPayments();
    }

    @GetMapping(path = "/allOrders")
    public ResponseEntity allOrders(){
        return adminService.allOrders();
    }

    @PostMapping(path = "/setProduct",
            consumes = {"multipart/form-data", "application/json"})
    public ResponseEntity setProduct(@RequestBody SetProductDto setProductDto){
        return adminService.setProduct(setProductDto);
    }

}
