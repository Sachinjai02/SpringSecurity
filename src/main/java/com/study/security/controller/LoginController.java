package com.study.security.controller;

import com.study.security.model.Customer;
import com.study.security.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    PasswordEncoder passwordEncoder;
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody Customer customer) {
        Customer savedCust = null;
        ResponseEntity<String> resp = null;

        try {
            customer.setPwd(passwordEncoder.encode(customer.getPwd()));
            savedCust = customerRepository.save(customer);
            resp = ResponseEntity.status(HttpStatus.CREATED)
                    .body("User is successfully registered");

        } catch(Exception ex) {
            resp = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred due to :" + ex.getMessage());
        }
        return resp;
    }

}
