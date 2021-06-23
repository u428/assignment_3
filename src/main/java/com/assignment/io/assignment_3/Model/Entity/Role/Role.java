package com.assignment.io.assignment_3.Model.Entity.Role;

import com.assignment.io.assignment_3.Model.Entity.Customer;
import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(nullable = false, name = "role_name", unique = true)
    @Enumerated(EnumType.STRING)
    private String roleName;

    @OneToOne(mappedBy = "role", fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    private List<Customer> customer;

    @OneToMany(mappedBy = "role")
    private List<Priviliges> priviliges;

}