package com.study.security.repository;

import com.study.security.model.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {
    //Derived method named query
    List<Customer> findByEmail(String email);

}
