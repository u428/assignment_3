package com.assignment.io.assignment_3.Model.Entity;


import com.assignment.io.assignment_3.Model.Entity.Role.Role;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20)
    private String name;

    @Column()
    private Character country;

    @Column()
    private String address;

    @Column(length = 9, unique = true)
    private int phone;

    @Column()
    private String password;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Order> order;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "role_id")
    private Role role;

}
