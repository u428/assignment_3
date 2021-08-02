package com.assignment.io.assignment_3.Service.ServiceImpl;

import com.assignment.io.assignment_3.Config.Depend.ApplicationUserRole;
import com.assignment.io.assignment_3.Config.Enams.OrderStatus;
import com.assignment.io.assignment_3.Model.Entity.*;
import com.assignment.io.assignment_3.Model.Entity.Role.Priviliges;
import com.assignment.io.assignment_3.Model.Entity.Role.Role;
import com.assignment.io.assignment_3.Model.ForLogin.CustomerSignUp;
import com.assignment.io.assignment_3.Repository.*;
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
import java.util.List;
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
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private DetailRepository detailRepository;

    @Override
    public ResponseEntity signUp(CustomerSignUp customerSignUp) {
        Customer customer=findByPhoneNumber(customerSignUp.getPhone());
        if (customer != null) throw new UsernameNotFoundException("telephone is not unique");
        customer=new Customer();
        BeanUtils.copyProperties(customerSignUp, customer);
        customer.setPassword(encoder.encode(customerSignUp.getPassword()));
        Role role= new Role();
        role.setRoleName(ApplicationUserRole.ADMIN.name());
        Set<Priviliges> set=new HashSet<>();
        roleRepository.save(role);
        for (String string: ApplicationUserRole.ADMIN.getGrantedAuthorities()){
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
    public ResponseEntity payment(String telNomer) {
        Customer customer=findByPhoneNumber(Integer.parseInt(telNomer));
        Order order=orderRepository.findOrderByCustomerIdAndStatus(customer.getId(), OrderStatus.STORED);
//        order.setStatus(OrderStatus.PAYED);
        Invoice invoice;
        if(order.getInvoice()==null){
            invoice=new Invoice();
            invoice.setIssued(new Date());
            double summ=0;
            for (Detail detail:order.getDetails()){
                summ+=detail.getProduct().getPrice()*detail.getQuantity();
            }
            invoice.setAmount(summ);
            invoice.setOrder(order);
        }else{
            invoice=order.getInvoice();
            double summ=0;
            for (Detail detail:order.getDetails()){
                summ+=detail.getProduct().getPrice()*detail.getQuantity();
            }
            invoice.setAmount(summ);
        }

//        invoice.setDue(new Date(invoice.getIssued().getTime()+15000));
        invoice.setDue(new Date(new Date().getTime()+30000));
        invoiceRepository.save(invoice);
        return ResponseEntity.ok(invoice);
    }

    @Override
    public ResponseEntity buy(double summa, String telNomer) {
        Customer customer = findByPhoneNumber(Integer.parseInt(telNomer));
        Order order=orderRepository.findOrderByCustomerIdAndStatus(customer.getId(), OrderStatus.STORED);
        Payment payment=new Payment();
        payment.setAmount(summa);
        payment.setInvoice(order.getInvoice());
        payment.setTime(new Date());
        System.out.println(order.getInvoice().getAmount());
        System.out.println(summa);
        if (order.getInvoice().getAmount()==summa){
            order.setStatus(OrderStatus.PAYED);
            payment.setInvoiceId(order.getInvoice().getId());
            paymentRepository.save(payment);
        }
        return ResponseEntity.ok(payment);
    }

    @Override
    public ResponseEntity getRole(String telnomer) {
        return ResponseEntity.ok(findByPhoneNumber(Integer.parseInt(telnomer)).getRole().getRoleName());
    }

    @Override
    public ResponseEntity deleteDetails(Long id) {
//        Detail detail=detailRepository.findById(id).get();
        detailRepository.deleteById(id);
        return ResponseEntity.ok("SUCCESS");
    }

    @Override
    public ResponseEntity updateDetails(Long id, short quantity, String telNomer) {
        Customer customer = findByPhoneNumber(Integer.parseInt(telNomer));
        Order order=orderRepository.findOrderByCustomerIdAndStatus(customer.getId(), OrderStatus.STORED);
        Detail detail=detailRepository.findById(id).get();
        detail.setQuantity(quantity);
        detailRepository.save(detail);

        return ResponseEntity.ok(detail);
    }

    @Override
    public ResponseEntity getAllInvoices(String telNomer) {
        Customer customer=findByPhoneNumber(Integer.parseInt(telNomer));
        List<Order> orderList=orderRepository.findAllByCustomerIdAndStatus(customer.getId(), OrderStatus.PAYED);
        return ResponseEntity.ok(orderList);
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
