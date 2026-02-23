package com.project.code.Repo;

import com.project.code.Model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    // 2. Find Customer by Email
    Customer findByEmail(String email);

    // 2. Find Customer by ID (JpaRepository already provides this,
    // but if explicitly declared, it should return Optional)
    Optional<Customer> findById(Long id);

    // 3. Additional Custom Query Methods

    // Find customers by name
    List<Customer> findByName(String name);

    // Find customer by phone
    Customer findByPhone(String phone);
}