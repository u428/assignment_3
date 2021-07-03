package com.assignment.io.assignment_3.Repository;

import com.assignment.io.assignment_3.Model.Entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    @Query(value = "select sum (amount) from payment p where p.time >= :start and p.time<= end")
    double getSummAmount(@Param("start") Date start,@Param("end") Date end);
}
