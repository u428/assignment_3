package com.assignment.io.assignment_3.Repository;

import com.assignment.io.assignment_3.Model.Entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {


    Customer findCustomerByPhone(int tel);
}
