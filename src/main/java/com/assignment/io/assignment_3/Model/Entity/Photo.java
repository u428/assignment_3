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

    @ManyToOne()
    private Product product;

}
