package com.assignment.io.assignment_3.Model.Entity.Role;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;

@Data
@Entity
@Table(name = "priviliges")
public class Priviliges {

    static Logger logger = LoggerFactory.getLogger(Priviliges.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column()
    private String name;

    @ManyToOne()
    private Role role;

    public Priviliges() {
    }
}
