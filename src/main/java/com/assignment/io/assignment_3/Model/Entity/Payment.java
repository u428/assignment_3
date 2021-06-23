package com.assignment.io.assignment_3.Model.Entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Date time;

    @Column
    private int amount;

    @ManyToOne()
    @JoinColumn(name = "invoice_id", referencedColumnName = "id")
    private Invoice invoice;

}
