package com.assignment.io.assignment_3.Service.ServiceImpl;

import com.assignment.io.assignment_3.Model.Entity.Customer;
import com.assignment.io.assignment_3.Model.ForLogin.CustomerSignUp;
import com.assignment.io.assignment_3.Repository.CustomerRepository;
import com.assignment.io.assignment_3.Service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public ResponseEntity signUp(CustomerSignUp customerSignUp) {
        Customer customer=findByPhoneNumber(customerSignUp.getPhone());
        if (customer != null) throw new UsernameNotFoundException("telepgone is not unique");

        return null;
    }

    @Override
    public ResponseEntity checkTelNomer(int tel) {
        Customer customer=findByPhoneNumber(tel);
        if (customer == null) return ResponseEntity.ok(1);
        return ResponseEntity.ok(0);
    }

    @Override
    public User loadUserByUsername(String s) throws UsernameNotFoundException {
        Customer user=customerRepository.findCustomerByPhone(Integer.parseInt(s));
        if (user == null) throw new UsernameNotFoundException(s);
        return new User(String.valueOf(user.getPhone()), user.getPassword(), getAuthority(user));
    }
    private Set<SimpleGrantedAuthority> getAuthority(Customer customer) {
        Set<SimpleGrantedAuthority> authorities = customer.getRole().getPriviliges().stream().map(priviliges ->
                new SimpleGrantedAuthority(priviliges.getName())).collect(Collectors.toSet());
        authorities.add(new SimpleGrantedAuthority("ROLE_"+customer.getRole().getRoleName()));
        return authorities;
    }

    private Customer findByPhoneNumber(int phone){
        return customerRepository.findCustomerByPhone(phone);
    }
}
