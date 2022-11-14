package com.study.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BalanceController {

    @GetMapping("/myBalances")
    public String getBalanceDetails() {
        return "Here are the balance details from the DB";
    }
}
