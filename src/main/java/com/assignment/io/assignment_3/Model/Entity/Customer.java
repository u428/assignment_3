package com.assignment.io.assignment_3.Model.Entity;


import com.assignment.io.assignment_3.Model.Entity.Role.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @Column(length = 20, nullable = false)
    private String name;

    @Column()
    private String country;

    @Column()
    private String address;

    @Column(length = 9, unique = true, nullable = false)
    private Integer phone;

    @JsonIgnore
    @Column(nullable = false)
    private String password;


    @OneToOne(mappedBy = "customer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Order order;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

}
