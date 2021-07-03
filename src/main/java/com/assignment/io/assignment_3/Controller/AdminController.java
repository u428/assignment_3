package com.assignment.io.assignment_3.Controller;

import com.assignment.io.assignment_3.Model.DTO.SetProductDto;
import com.assignment.io.assignment_3.Service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Date;

@RestController
@RequestMapping(path = "/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping(path = "/allPayments")
    public ResponseEntity allPayments(){
        return adminService.allPayments();
    }

    @GetMapping(path = "/allInvoices")
    public ResponseEntity allInvoices(){
        return adminService.allInvoices();
    }

    @PostMapping(path = "/addProduct",
            consumes = {"multipart/form-data", "application/json"})
    public ResponseEntity setProduct(@ModelAttribute SetProductDto setProductDto) throws IOException {
        return adminService.setProduct(setProductDto);
    }

    @PutMapping(path="/putProduct/{id}")
    public ResponseEntity putProduct(@PathVariable Long id, @RequestBody SetProductDto setProductDto){
        return adminService.putProduct(id, setProductDto);
    }

    @DeleteMapping(path = "/deleteProduct/{id}")
    public ResponseEntity deleteProduct(@PathVariable Long id){
        return adminService.deleteProduct(id);
    }

    @PutMapping(path = "/changeProductPhoto")
    public ResponseEntity changeProductPhoto(@RequestParam Long productPhotoId, @ModelAttribute MultipartFile multipartFile){
        return adminService.changeProductPhoto(productPhotoId, multipartFile);
    }

    @DeleteMapping(path = "/deleteProductPhoto/{id}")
    public ResponseEntity deleteProductPhoto(@PathVariable Long id){
        return adminService.deleteProductPhoto(id);
    }

    @PostMapping(path = "/addCategory")
    public ResponseEntity addCategory(@RequestParam String categoryName){
        return adminService.addCategory(categoryName);
    }

    @PutMapping(path = "/putcategory/{id}")
    public ResponseEntity putcategory(@PathVariable Long id, @RequestParam String categoryName){
        return adminService.putcategory(id, categoryName);
    }

    @DeleteMapping(path = "/deleteCategory/{id}")
    public ResponseEntity deleteCategory(@PathVariable Long id){
        return adminService.deleteCategory(id);
    }

    @GetMapping(path = "/getIncome")
    public ResponseEntity getIncome(@RequestParam Date start, @RequestParam Date end){
        return adminService.getIncome(start, end);
    }

}
