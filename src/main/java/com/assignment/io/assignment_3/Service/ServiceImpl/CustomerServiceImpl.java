package com.assignment.io.assignment_3.Service.ServiceImpl;

import com.assignment.io.assignment_3.Config.Depend.ApplicationUserRole;
import com.assignment.io.assignment_3.Model.Entity.Customer;
import com.assignment.io.assignment_3.Model.Entity.Order;
import com.assignment.io.assignment_3.Model.Entity.Role.Priviliges;
import com.assignment.io.assignment_3.Model.Entity.Role.Role;
import com.assignment.io.assignment_3.Model.ForLogin.CustomerSignUp;
import com.assignment.io.assignment_3.Repository.CustomerRepository;
import com.assignment.io.assignment_3.Repository.PriviligesRepository;
import com.assignment.io.assignment_3.Repository.RoleRepository;
import com.assignment.io.assignment_3.Service.CustomerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private BCryptPasswordEncoder encoder;
    @Autowired
    private PriviligesRepository priviligesRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public ResponseEntity signUp(CustomerSignUp customerSignUp) {
        Customer customer=findByPhoneNumber(customerSignUp.getPhone());
        if (customer != null) throw new UsernameNotFoundException("telephone is not unique");
        customer=new Customer();
        BeanUtils.copyProperties(customerSignUp, customer);
        customer.setPassword(encoder.encode(customerSignUp.getPassword()));
        Order order=new Order();
        order.setDate(new Date());
        customer.setOrder(order);
        Role role= new Role();
        role.setRoleName(ApplicationUserRole.USER.name());
        Set<Priviliges> set=new HashSet<>();
        roleRepository.save(role);
        for (String string: ApplicationUserRole.USER.getGrantedAuthorities()){
            Priviliges priviliges=new Priviliges();
            priviliges.setName(string);
            priviliges.setRoleId(role.getId());
            priviligesRepository.save(priviliges);
            set.add(priviliges);
        }
//        role.setPriviliges(set);
        customer.setRole(role);
        customer = customerRepository.save(customer);
        return ResponseEntity.ok(customer);
    }

    @Override
    public ResponseEntity checkTelNomer(int tel) {
        Customer customer=findByPhoneNumber(tel);
        if (customer == null) return ResponseEntity.ok(1);
        return ResponseEntity.ok(0);
    }

    @Override
    public ResponseEntity getCurrentCustomer(String telNomer) {
        return ResponseEntity.ok(findByPhoneNumber(Integer.parseInt(telNomer)));
    }

    @Override
    public User loadUserByUsername(String s) throws UsernameNotFoundException {
        Customer user=customerRepository.findCustomerByPhone(Integer.parseInt(s));
        if (user == null) throw new UsernameNotFoundException(s);
        return new User(String.valueOf(user.getPhone()), user.getPassword(), getAuthority(user));
    }
    private Set<SimpleGrantedAuthority> getAuthority(Customer customer) {
        Set<SimpleGrantedAuthority> authorities = priviligesRepository.findAllByRoleId(customer.getRole().getId()).stream().map(priviliges ->
                new SimpleGrantedAuthority(priviliges.getName())).collect(Collectors.toSet());
        authorities.add(new SimpleGrantedAuthority("ROLE_"+customer.getRole().getRoleName()));
        return authorities;
    }

    private Customer findByPhoneNumber(int phone){
        return customerRepository.findCustomerByPhone(phone);
    }
}
