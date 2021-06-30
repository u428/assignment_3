package com.assignment.io.assignment_3.Service.ServiceImpl;

import com.assignment.io.assignment_3.Model.DTO.OrderDTO;
import com.assignment.io.assignment_3.Model.Entity.*;
import com.assignment.io.assignment_3.Repository.DetailRepository;
import com.assignment.io.assignment_3.Repository.InvoiceRepository;
import com.assignment.io.assignment_3.Repository.OrderRepository;
import com.assignment.io.assignment_3.Repository.ProductRepository;
import com.assignment.io.assignment_3.Service.OtherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OtherServiceImpl implements OtherService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private DetailRepository detailRepository;


    @Override
    public ResponseEntity<List<Product>> getAllProducts() {
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
    public ResponseEntity karzinka(OrderDTO orderDTO, Customer customer) {
       Detail detail=new Detail();
//       productRepository.findById(orderDTO.getProduct_id());
       detail.setQuantity(orderDTO.getProduct_quantity());
       detail.setProductId(orderDTO.getProduct_id());
       detail.setOrderId(customer.getOrder().getId());
       detailRepository.save(detail);
       return ResponseEntity.ok(detail);
    }
}
