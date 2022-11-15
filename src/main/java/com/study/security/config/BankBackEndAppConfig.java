package com.study.security.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

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


    /**
     * Note: Due to presence of this explicit bean, application.properties username/pwd is now ignored.
     *
     */
    /*@Bean
    public InMemoryUserDetailsManager userDetailsService() {
        /*UserDetails admin = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("adminpw")
                .authorities("admin")
                .build();

        UserDetails user = User.withDefaultPasswordEncoder()
                .username("user")
                .password("userpw")
                .authorities("admin")
                .build();*/

        /**
         * With NoopPassword encoder.
         */
        /*
        UserDetails admin = User.withUsername("admin")
                .password("adminpw")
                .authorities("admin")
                .build();

        UserDetails user = User.withUsername("user")
                .password("userpw")
                .authorities("admin")
                .build();

        return new InMemoryUserDetailsManager(admin, user);
    }*/
    @Bean
    public UserDetailsService jdbcUserDetailsService(DataSource ds) {
        return new JdbcUserDetailsManager(ds);
    }

    @Bean
    public PasswordEncoder noOpPasswordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
