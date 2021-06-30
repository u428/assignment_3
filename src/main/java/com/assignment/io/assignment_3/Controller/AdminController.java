package com.assignment.io.assignment_3.Controller;

import com.assignment.io.assignment_3.Model.DTO.SetProductDto;
import com.assignment.io.assignment_3.Service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

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
    public ResponseEntity setProduct(@ModelAttribute SetProductDto setProductDto) throws IOException {
        return adminService.setProduct(setProductDto);
    }

    @PostMapping(path = "/addCategort")
    public ResponseEntity addCategort(@RequestParam String categoryName){
        return adminService.addCategort(categoryName);
    }

}
