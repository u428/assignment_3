package com.assignment.io.assignment_3.Service.ServiceImpl;

import com.assignment.io.assignment_3.Model.DTO.SetProductDto;
import com.assignment.io.assignment_3.Model.Entity.Category;
import com.assignment.io.assignment_3.Model.Entity.Photo;
import com.assignment.io.assignment_3.Model.Entity.Product;
import com.assignment.io.assignment_3.Repository.CategoryRepository;
import com.assignment.io.assignment_3.Repository.Photorepository;
import com.assignment.io.assignment_3.Repository.ProductRepository;
import com.assignment.io.assignment_3.Service.AdminService;
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

    public AdminServiceImpl(@Value("java-code") String fileStorageLocation) {
        this.fileStorageLocation = fileStorageLocation;
        fileStoragePath =Paths.get(fileStorageLocation).toAbsolutePath().normalize();
    }

    @Override
    public ResponseEntity allOrders() {
        return null;
    }

    @Override
    public ResponseEntity allPayments() {
        return null;
    }

    @Override
    public ResponseEntity setProduct(SetProductDto setProductDto) throws IOException {
        Product product=new Product();
        List<Photo> photoList=new ArrayList<>();
        product.setName(setProductDto.getName());
        product.setDescription(setProductDto.getDescription());
        product.setPrice(setProductDto.getPrice());
        product.setCategory(categoryRepository.findById(setProductDto.getCategoryId()).get());

        for (MultipartFile multipartFile: setProductDto.getFiles()) {
            Photo photo=new Photo();
            photo.setContentType(multipartFile.getContentType());
            photo.setName(multipartFile.getOriginalFilename());
            photo.setFileSize(multipartFile.getSize());
            photorepository.save(photo);
            String AA=multipartFile.getOriginalFilename();
            String fileName = String.valueOf(photo.getId())+AA.substring(AA.length()-4, AA.length());
            photo.setExtention(AA.substring(AA.length()-4));

            Path filePath = Paths.get(fileStoragePath + "//" + fileName);
            photo.setUploadPath(String.valueOf(filePath));
            Files.copy(multipartFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
//            photorepository.save(photo);
            photoList.add(photo);
        }
        product.setPhotos(photoList);
        productRepository.save(product);

        return ResponseEntity.ok(product);
    }

    @Override
    public ResponseEntity addCategort(String categoryName) {
        Category category=new Category();
        category.setName(categoryName);
        return ResponseEntity.ok(category);
    }
}
