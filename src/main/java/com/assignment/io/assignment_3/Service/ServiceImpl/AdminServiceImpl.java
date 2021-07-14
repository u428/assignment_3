package com.assignment.io.assignment_3.Service.ServiceImpl;

import com.assignment.io.assignment_3.Config.Enams.OrderStatus;
import com.assignment.io.assignment_3.Model.DTO.SetProductDto;
import com.assignment.io.assignment_3.Model.Entity.Category;
import com.assignment.io.assignment_3.Model.Entity.Order;
import com.assignment.io.assignment_3.Model.Entity.Photo;
import com.assignment.io.assignment_3.Model.Entity.Product;
import com.assignment.io.assignment_3.Repository.*;
import com.assignment.io.assignment_3.Service.AdminService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    private Path fileStoragePath;
    private String fileStorageLocation;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private Photorepository photorepository;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private OrderRepository orderRepository;

    public AdminServiceImpl(@Value("java-code") String fileStorageLocation) {
        this.fileStorageLocation = fileStorageLocation;
        fileStoragePath =Paths.get(fileStorageLocation).toAbsolutePath().normalize();
    }

    @Override
    public ResponseEntity allPayments() {
        return ResponseEntity.ok(paymentRepository.findAll());
    }

    @Override
    public ResponseEntity setProduct(SetProductDto setProductDto) throws IOException {
        Product product=new Product();
        List<Photo> photoList=new ArrayList<>();
        product.setName(setProductDto.getName());
        product.setDescription(setProductDto.getDescription());
        product.setPrice(setProductDto.getPrice());
//        product.setCategory(categoryRepository.findById(setProductDto.getCategoryId()).get());
        product.setCategoryId(setProductDto.getCategoryId());
        product.setCreatedDate(new Date());
        productRepository.save(product);
        for (MultipartFile multipartFile: setProductDto.getFiles()) {
            Photo photo=new Photo();
            photo.setContentType(multipartFile.getContentType());
            photo.setName(multipartFile.getOriginalFilename());
            photo.setFileSize(multipartFile.getSize());
            photo.setProductId(product.getId());
            photorepository.save(photo);
            String AA=multipartFile.getOriginalFilename();
            String fileName = String.valueOf(photo.getId())+AA.substring(AA.length()-4, AA.length());
            photo.setExtention(AA.substring(AA.length()-4));

            Path filePath = Paths.get(fileStoragePath + "//" + fileName);
            photo.setUploadPath(String.valueOf(filePath));
            Files.copy(multipartFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            photo.setProduct(product);
            photoList.add(photo);
        }
        product.setPhotos(photoList);
        productRepository.save(product);

        return ResponseEntity.ok(product);
    }

    @Override
    public ResponseEntity addCategory(String categoryName) {
        Category category=new Category();
        category.setName(categoryName);
        categoryRepository.save(category);
        return ResponseEntity.ok(category);
    }

    @Override
    public ResponseEntity allInvoices() {
        List<Order> orderList=orderRepository.findAllByStatus(OrderStatus.PAYED);
        return ResponseEntity.ok(orderList);
    }

    @Override
    public ResponseEntity putProduct(Long id, SetProductDto setProductDto) {
        Product product=productRepository.findById(id).get();
        BeanUtils.copyProperties(setProductDto, product);
        productRepository.save(product);
        return ResponseEntity.ok(product);
    }

    @Override
    public ResponseEntity deleteProduct(Long id) throws IOException {
        Product product=productRepository.findById(id).get();
        for(Photo photo:product.getPhotos()){
            Path path=Paths.get(photo.getUploadPath());
            try {
                Files.delete(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
            photorepository.delete(photo);
        }
        productRepository.delete(product);
        return ResponseEntity.ok("SUCCESS");
    }

    @Override
    public ResponseEntity putcategory(Long id, String categoryName) {
        Category category=categoryRepository.findById(id).get();
        category.setName(categoryName);
        categoryRepository.save(category);
        return ResponseEntity.ok(category);
    }

    @Override
    public ResponseEntity deleteCategory(Long id) {
        Category category=categoryRepository.findById(id).get();
        categoryRepository.delete(category);
        return ResponseEntity.ok("SUCCESS");
    }

    @Override
    public ResponseEntity changeProductPhoto(Long productPhotoId, MultipartFile multipartFile) {
        String AA=multipartFile.getOriginalFilename();
        Photo photo=photorepository.findById(productPhotoId).get();
        photo.setFileSize(multipartFile.getSize());
        photo.setName(multipartFile.getOriginalFilename());
        photo.setContentType(multipartFile.getContentType());

        Path path=Paths.get(photo.getUploadPath());
        String fileName = String.valueOf(photo.getId())+AA.substring(AA.length()-4, AA.length());
        Path filePath = Paths.get(fileStoragePath + "//" + fileName);

        photo.setUploadPath(String.valueOf(filePath));
        photo.setExtention(AA.substring(AA.length()-4));
        photorepository.save(photo);

        try {
            System.out.println(filePath);
            System.out.println(path);

            Files.delete(path);
            Files.copy(multipartFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            return ResponseEntity.ok(photo);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ResponseEntity deleteProductPhoto(Long id) {
        Photo photo = photorepository.findById(id).get();
        Path path=Paths.get(photo.getUploadPath());
        try {
            Files.delete(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        photorepository.delete(photo);
        return ResponseEntity.ok("SUCCESS");
    }

    @Override
    public ResponseEntity getIncome(Date start, Date end) {
//        double summ = paymentRepository.getSummAmount(start, end);
//        return ResponseEntity.ok(summ);
        return null;
    }
}
