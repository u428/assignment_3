package com.assignment.io.assignment_3.Repository;

import com.assignment.io.assignment_3.Model.Entity.Detail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetailRepository extends JpaRepository<Detail, Long> {

    Detail findDetailByOrderIdAndProductId(Long orderIdid, Long productId);
}
