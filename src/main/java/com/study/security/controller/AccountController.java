package com.study.security.controller;

import com.study.security.model.Accounts;
import com.study.security.model.Customer;
import com.study.security.repository.AccountsRepository;
import com.study.security.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AccountController {

    @Autowired
    private AccountsRepository accountsRepository;


    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/myAccount")
    public Accounts getAccountDetails(@RequestParam String email) {
        List<Customer> customerList = customerRepository.findByEmail(email);
        if(customerList == null || customerList.isEmpty()) {
            return null;
        }
        Customer customer = customerList.get(0);
        Accounts accounts = accountsRepository.findByCustomerId(customer.getId());
        if (accounts != null ) {
            return accounts;
        }else {
            return null;
        }
    }

}
