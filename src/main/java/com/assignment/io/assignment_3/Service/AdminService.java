package com.assignment.io.assignment_3.Service;

import com.assignment.io.assignment_3.Model.DTO.SetProductDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;

public interface AdminService {

    ResponseEntity allPayments();

    ResponseEntity setProduct(SetProductDto setProductDto) throws IOException;

    ResponseEntity addCategory(String categoryName);

    ResponseEntity allInvoices();

    ResponseEntity putProduct(Long id, SetProductDto setProductDto);

    ResponseEntity deleteProduct(Long id) throws IOException;

    ResponseEntity putcategory(Long id, String categoryName);

    ResponseEntity deleteCategory(Long id);

    ResponseEntity changeProductPhoto(Long productPhotoId, MultipartFile multipartFile);

    ResponseEntity deleteProductPhoto(Long id);

    ResponseEntity getIncome(Date start, Date end);
}
