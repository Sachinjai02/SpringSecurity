package com.study.security.config;

import com.study.security.model.Customer;
import com.study.security.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DemoBankAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String userName = authentication.getName();
        String pwd = authentication.getCredentials().toString();
        List<Customer> customers = customerRepository.findByEmail(userName);
        if(customers.size() == 0) {
            throw new BadCredentialsException("No user registered with this emailId!");
        }

        Customer customer = customers.get(0);
        if(passwordEncoder.matches(pwd, customer.getPwd())) {
            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.addAll(customer.getAuthorities().stream().
                    map(s -> new SimpleGrantedAuthority(s.getName())).collect(Collectors.toList()));
            return new UsernamePasswordAuthenticationToken(userName, pwd, authorities);
        } else {
            throw new BadCredentialsException("Invalid credentials");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
