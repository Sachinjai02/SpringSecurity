package com.study.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.study.security")
public class DemoBankBackEndApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoBankBackEndApplication.class, args);
    }

}
