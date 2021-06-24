package com.assignment.io.assignment_3.Model.Entity;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "details")
public class Detail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private short quantity;

    @Column(name = "product_id")
    private Long productId;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;

    @ManyToOne()
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    private Product product;

}
