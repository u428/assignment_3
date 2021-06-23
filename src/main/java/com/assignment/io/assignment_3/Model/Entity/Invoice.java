package com.assignment.io.assignment_3.Model.Entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Data
@Entity
@Table(name = "invoice")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private double amount;

    @Temporal(TemporalType.DATE)
    @Column
    private Date issued;

    @Temporal(TemporalType.DATE)
    @Column
    private Date due;

    @OneToOne()
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;

    @OneToMany(mappedBy = "invoice")
    private List<Payment> payment;
}
