package com.assignment.io.assignment_3.Model.Entity;


import lombok.Data;

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
    @Column
    private Date date;

    @ManyToOne()
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    @OneToOne(mappedBy = "order")
    private Invoice invoice;

    @OneToMany(mappedBy = "order")
    private List<Detail> details;

}
