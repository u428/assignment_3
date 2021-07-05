package com.assignment.io.assignment_3.Service.ServiceImpl;

import com.assignment.io.assignment_3.Config.Enams.OrderStatus;
import com.assignment.io.assignment_3.Model.DTO.OrderDTO;
import com.assignment.io.assignment_3.Model.Entity.*;
import com.assignment.io.assignment_3.Repository.*;
import com.assignment.io.assignment_3.Service.OtherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class OtherServiceImpl implements OtherService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private DetailRepository detailRepository;
    @Autowired
    private Photorepository photorepository;


    @Override
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> productList=productRepository.findAll();
        return ResponseEntity.ok(productRepository.findAll());
    }

    @Override
    public ResponseEntity<Product> getProductById(Long id) {
        return ResponseEntity.ok(productRepository.findById(id).get());
    }

    @Override
    public ResponseEntity customOrder(List<OrderDTO> orderDTOList, Customer customer) {
//        Product product=productRepository.findById(id).get()
        double summ=0;
        List<Detail> details=new ArrayList<>();
        for (OrderDTO orderDTO:orderDTOList) {
           Detail detail =new Detail();
           Product product=productRepository.findById(orderDTO.getProduct_id()).get();
           detail.setProduct(product);
           detail.setQuantity(orderDTO.getProduct_quantity());
           summ+=product.getPrice();
           details.add(detail);
        }
        Order order=new Order();
        order.setDetails(details);
        order.setCustomer(customer);

        Invoice invoice=new Invoice();
        invoice.setAmount(summ);
        order.setInvoice(invoice);
        orderRepository.save(order);

        Map<String , Long> map=new HashMap<>();
        map.put("Order_id", order.getId());
        map.put("Invoice_id", invoice.getId());
        return ResponseEntity.ok(map);
    }

    @Override
    public ResponseEntity sortProductByCategory(Long id) {
        return null;
    }

    @Override
    public ResponseEntity karzinka(OrderDTO orderDTO, String userName) {
        Order order=orderRepository.findOrderByCustomerIdAndStatus(findByPhoneNumber(userName),OrderStatus.STORED);
        if (order == null) {
            order = new Order();
            order.setDate(new Date());
            order.setCustomerId(findByPhoneNumber(userName));
            order.setStatus(OrderStatus.STORED);
            orderRepository.save(order);
        }
       Detail detail=new Detail();
       detail.setQuantity(orderDTO.getProduct_quantity());
       detail.setProductId(orderDTO.getProduct_id());
       detail.setOrderId(order.getId());
       detailRepository.save(detail);
       return ResponseEntity.ok(detail);
    }

    @Override
    public ResponseEntity getKarzinka(String telNomer) {
        Order order=orderRepository.findOrderByCustomerIdAndStatus(findByPhoneNumber(telNomer),OrderStatus.STORED);
        return ResponseEntity.ok(order);
    }

    @Override
    public ResponseEntity image(Long id) throws MalformedURLException {
        Photo photo=photorepository.findById(id).get();
        Path path= Paths.get(photo.getUploadPath());
        Resource resource= new UrlResource(path.toUri());
        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(photo.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline;fileName="+resource.getFilename())
                .body(resource);
    }

    private Long findByPhoneNumber(String phone){
        return customerRepository.findCustomerByPhone(Integer.parseInt(phone)).getId();
    }
}
