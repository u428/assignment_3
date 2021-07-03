package com.assignment.io.assignment_3.Controller;

import com.assignment.io.assignment_3.Model.Entity.Product;
import com.assignment.io.assignment_3.Service.OtherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/all")
public class AllController {

    @Autowired
    private OtherService otherService;

    @GetMapping(path = "/sortProductByCategory/{id}")
    public ResponseEntity sortProductByCategory(@PathVariable Long id){
        return otherService.sortProductByCategory(id);
    }

    @GetMapping(path = "/getProducts")
    public ResponseEntity<List<Product>> getAllProduct(){
        return otherService.getAllProducts();
    }

    @GetMapping(path = "/getproduct/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id){
        return otherService.getProductById(id);
    }
}
