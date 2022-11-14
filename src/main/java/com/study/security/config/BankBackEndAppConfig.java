package com.study.security.config;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class BankBackEndAppConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .requestMatchers("/myAccounts", "/myBalances", "/myLoans", "/myCards").authenticated()
                .requestMatchers("/notices", "/contact").permitAll()
                .and().httpBasic()
                .and().formLogin();

        /**
         * Achieving denyAll
         */

        /*
        http.authorizeHttpRequests()
                .anyRequest().denyAll()
                .and().httpBasic()
                .and().formLogin(); */

        /**
         * Achieving permitAll
         */

        /*
        http.authorizeHttpRequests()
                .anyRequest().permitAll()
                .and().httpBasic()
                .and().formLogin();
        */

        return http.build();
    }
}
