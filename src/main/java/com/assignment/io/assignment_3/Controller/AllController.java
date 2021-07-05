package com.assignment.io.assignment_3.Controller;

import com.assignment.io.assignment_3.Model.Entity.Product;
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

    @PreAuthorize("hasAuthority('read')")
    @GetMapping(path = "/sortProductByCategory/{id}")
    public ResponseEntity sortProductByCategory(@PathVariable Long id){
        return otherService.sortProductByCategory(id);
    }

    @PreAuthorize("hasAuthority('read')")
    @GetMapping(path = "/getProducts")
    public ResponseEntity<List<Product>> getAllProduct(){
        return otherService.getAllProducts();
    }

    @PreAuthorize("hasAuthority('read')")
    @GetMapping(path = "/getproduct/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id){
        return otherService.getProductById(id);
    }

    @PreAuthorize("hasAuthority('read')")
    @GetMapping(path = "/image/{id}")
    public ResponseEntity image(@PathVariable Long id) throws MalformedURLException {
        return otherService.image(id);
    }


}
