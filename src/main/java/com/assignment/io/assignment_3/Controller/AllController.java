package com.assignment.io.assignment_3.Controller;

import com.assignment.io.assignment_3.Model.Entity.Product;
import com.assignment.io.assignment_3.Security.CurrentUser;
import com.assignment.io.assignment_3.Service.CustomerService;
import com.assignment.io.assignment_3.Service.OtherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.util.List;

@RestController
@RequestMapping(path = "/all")
public class AllController {

    @Autowired
    private OtherService otherService;
    @Autowired
    private CustomerService customerService;

    @GetMapping(path = "/sortProductByCategory")
    public ResponseEntity sortProductByCategory(@RequestParam Long id, @RequestParam int limit, @RequestParam int page){
        return otherService.sortProductByCategory(id, limit, page);
    }

    @GetMapping(path = "/getProducts")
    public ResponseEntity getAllProduct(@RequestParam int limit, @RequestParam int page){
        return otherService.getAllProducts(page, limit);
    }

    @PreAuthorize("hasAuthority('read')")
    @GetMapping(path = "/getproduct")
    public ResponseEntity<Product> getProductById(@RequestParam Long id){
        return otherService.getProductById(id);
    }


//    @GetMapping(path = "/image")
//    public ResponseEntity image(@RequestParam Long id) throws MalformedURLException {
//        return otherService.image(id);
//    }

    @PreAuthorize("hasAuthority('read')")
    @GetMapping(path = "/getAllCategory")
    public ResponseEntity getAllCategpry(){
        return otherService.getAllcategory();
    }

    @PreAuthorize("hasAuthority('read')")
    @GetMapping(path = "/getCurrentCustomer")
    public ResponseEntity getCurrentCustomer(@CurrentUser String telNomer){
        return customerService.getCurrentCustomer(telNomer);
    }

    @PreAuthorize("hasAuthority('read')")
    @GetMapping(path="/getRole")
    public ResponseEntity getRole(@CurrentUser String telnomer){
        return customerService.getRole(telnomer);
    }



}
