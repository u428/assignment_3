package com.assignment.io.assignment_3.Model.Entity;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "photo")
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String extention;

    @Column
    private Long fileSize;

    @Column
    private String contentType;

    @Column
    private String uploadPath;

    @Column(name = "product_id")
    private Long productId;

    @ManyToOne()
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    private Product product;

}
