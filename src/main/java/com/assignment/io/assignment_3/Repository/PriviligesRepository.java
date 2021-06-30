package com.assignment.io.assignment_3.Repository;

import com.assignment.io.assignment_3.Model.Entity.Role.Priviliges;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PriviligesRepository extends JpaRepository<Priviliges, Long> {

    List<Priviliges> findAllByRoleId(Long id);
}
