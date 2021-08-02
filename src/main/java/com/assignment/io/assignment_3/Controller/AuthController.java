package com.assignment.io.assignment_3.Controller;


import com.assignment.io.assignment_3.Model.ForLogin.CustomerSignUp;
import com.assignment.io.assignment_3.Service.CustomerService;
import com.assignment.io.assignment_3.Service.OtherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.MalformedURLException;

@RestController
@RequestMapping(path = "/auth")
public class AuthController {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private OtherService otherService;

    @PostMapping(path = "/signUp")
    public ResponseEntity signUp(@Valid @RequestBody CustomerSignUp customerSignUp, BindingResult bindingResult){
        if (bindingResult.hasErrors()) return null;
        System.out.println(customerSignUp.getPhone());
        return customerService.signUp(customerSignUp);
    }

    @GetMapping(path = "/checkTelNomer")
    public ResponseEntity checkTelNomer(@RequestParam int tel){
        return customerService.checkTelNomer(tel);
    }

    @GetMapping(path = "/image")
    public ResponseEntity image(@RequestParam Long id) throws MalformedURLException {
        return otherService.image(id);
    }
}
