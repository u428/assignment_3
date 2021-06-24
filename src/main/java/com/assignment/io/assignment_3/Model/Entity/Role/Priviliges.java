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

    @Column(name = "role_id")
    private Long roleId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "role_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Role role;

    public Priviliges() {
    }
}
