package com.assignment.io.assignment_3.Service.ServiceImpl;

import com.assignment.io.assignment_3.Config.Enams.OrderStatus;
import com.assignment.io.assignment_3.Model.DTO.OrderDTO;
import com.assignment.io.assignment_3.Model.Entity.*;
import com.assignment.io.assignment_3.Repository.*;
import com.assignment.io.assignment_3.Service.OtherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

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
    @Autowired
    private CategoryRepository categoryRepository;


    @Override
    public ResponseEntity getAllProducts(int page, int limit) {
        if (page>0) --page;
        Pageable pageable= PageRequest.of(page, limit);
        Page<Product> productsPage=productRepository.findAll(pageable);
        return ResponseEntity.ok(productsPage);
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
    public ResponseEntity sortProductByCategory(Long id, int limit, int page) {
        if (page>0) --page;
        Pageable pageable= PageRequest.of(page, limit);
        Page<Product> categoryPage=productRepository.findByCategoryId(id, pageable);
        return ResponseEntity.ok(categoryPage);
    }

    @Override
    public ResponseEntity karzinka(OrderDTO orderDTO, String userName) {
        System.out.println(orderDTO);
        Order order=orderRepository.findOrderByCustomerIdAndStatus(findByPhoneNumber(userName),OrderStatus.STORED);
        if (order == null) {
            order = new Order();
            order.setDate(new Date());
            order.setCustomerId(findByPhoneNumber(userName));
            order.setStatus(OrderStatus.STORED);
            orderRepository.save(order);
            addDetails(orderDTO, order.getId());
        }
        else if(order.getDetails()==null){
            addDetails(orderDTO, order.getId());
        }
        else{
            Detail detail=detailRepository.findDetailByOrderIdAndProductId(order.getId(), orderDTO.getProduct_id());
            if (detail==null){
                addDetails(orderDTO, order.getId());
            }else{
                detail.setQuantity((short) (detail.getQuantity()+1));
                detailRepository.save(detail);
            }
        }
       return ResponseEntity.ok("SUCCESS");
    }

    @Override
    public ResponseEntity getKarzinka(String telNomer) {
        Order order=orderRepository.findOrderByCustomerIdAndStatus(findByPhoneNumber(telNomer),OrderStatus.STORED);
        return ResponseEntity.ok(order.getDetails());
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

    @Override
    public ResponseEntity getAllcategory() {
        return ResponseEntity.ok(categoryRepository.findAll());
    }

    private Long findByPhoneNumber(String phone){
        return customerRepository.findCustomerByPhone(Integer.parseInt(phone)).getId();
    }

    private void addDetails(OrderDTO orderDTO, Long id){
        Detail detail=new Detail();
        detail.setQuantity(orderDTO.getProduct_quantity());
        detail.setProductId(orderDTO.getProduct_id());
        detail.setOrderId(id);
        detailRepository.save(detail);
    }
}
