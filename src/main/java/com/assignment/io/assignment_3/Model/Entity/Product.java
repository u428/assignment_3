package com.assignment.io.assignment_3.Model.Entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private double price;

    @Temporal(TemporalType.DATE)
    @CreatedDate
    private Date createdAt;

    @Column(name = "category_id")
    private Long categoryId;

    @OneToMany(mappedBy = "product")
    private List<Photo> photos;

    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private List<Detail> details;

    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "category_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Category category;

}
