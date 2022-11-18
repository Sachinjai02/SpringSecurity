package com.study.security.controller;

import com.study.security.model.Customer;
import com.study.security.model.Loans;
import com.study.security.repository.CustomerRepository;
import com.study.security.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LoansController {

    @Autowired
    private LoanRepository loanRepository;


    @Autowired
    private CustomerRepository customerRepository;
    @GetMapping("/myLoans")
    @PostAuthorize("hasRole('USER')")
    public List<Loans> getLoanDetails(@RequestParam String email) {
        List<Customer> customerList = customerRepository.findByEmail(email);
        if(customerList == null || customerList.isEmpty()) {
            return null;
        }
        Customer customer = customerList.get(0);
        List<Loans> loans = loanRepository.findByCustomerIdOrderByStartDtDesc(customer.getId());
        if (loans != null ) {
            return loans;
        }else {
            return null;
        }
    }

}
