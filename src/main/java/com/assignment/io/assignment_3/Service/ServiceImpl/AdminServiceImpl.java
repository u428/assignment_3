package com.assignment.io.assignment_3.Service.ServiceImpl;

import com.assignment.io.assignment_3.Model.DTO.SetProductDto;
import com.assignment.io.assignment_3.Repository.ProductRepository;
import com.assignment.io.assignment_3.Service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.PrivateKey;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public ResponseEntity allOrders() {
        return null;
    }

    @Override
    public ResponseEntity getRoles() {
        return null;
    }

    @Override
    public ResponseEntity createRole(String name) {
        return null;
    }

    @Override
    public ResponseEntity allPayments() {
        return null;
    }

    @Override
    public ResponseEntity setProduct(SetProductDto setProductDto) {
        



        return null;
    }
}
