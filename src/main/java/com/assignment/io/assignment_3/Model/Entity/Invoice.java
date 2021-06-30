package com.assignment.io.assignment_3.Model.Entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "invoices")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private double amount;

    @Temporal(TemporalType.DATE)
    @CreatedDate
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
