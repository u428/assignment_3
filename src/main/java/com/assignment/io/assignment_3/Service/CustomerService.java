package com.assignment.io.assignment_3.Service;

import com.assignment.io.assignment_3.Model.ForLogin.CustomerSignUp;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface CustomerService extends UserDetailsService {
    ResponseEntity signUp(CustomerSignUp customerSignUp);

    ResponseEntity checkTelNomer(int tel);
}
