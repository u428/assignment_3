package com.assignment.io.assignment_3.Model.Entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.DATE)
    @CreatedDate
    @Column
    private Date date;

    @JsonIgnore
    @OneToOne()
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @JsonIgnore
    @OneToOne(mappedBy = "order")
    private Invoice invoice;

    @JsonIgnore
    @OneToMany(mappedBy = "order")
    private List<Detail> details;

}
