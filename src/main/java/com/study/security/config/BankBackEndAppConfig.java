package com.study.security.config;


import com.study.security.filter.AuthenticationLoggingAtFilter;
import com.study.security.filter.LogAuthoritiesAfterFilter;
import com.study.security.filter.RequestValidationBeforeFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
public class BankBackEndAppConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .securityContext().requireExplicitSave(false).
                and()
                .csrf()
                .disable()
                //.ignoringRequestMatchers("/contact", "/register")
                //.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                //.and()
                .cors().configurationSource(new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                        CorsConfiguration corsConfig = new CorsConfiguration();
                        corsConfig.setAllowedOrigins(Arrays.stream(new String[]{"http://sachinsach.abc:4200","http://localhost:4200"}).toList());
                        corsConfig.setAllowCredentials(true);
                        corsConfig.setAllowedMethods(Collections.singletonList("*"));
                        corsConfig.setAllowedHeaders(Collections.singletonList("*"));
                        corsConfig.setMaxAge(3600l);
                        return corsConfig;
                    }
                })
                .and().addFilterBefore(new RequestValidationBeforeFilter(), BasicAuthenticationFilter.class)
                .addFilterAt(new AuthenticationLoggingAtFilter(), BasicAuthenticationFilter.class)
                .addFilterAfter(new LogAuthoritiesAfterFilter(), BasicAuthenticationFilter.class)
                .authorizeHttpRequests()
                /*.requestMatchers("/myAccount").hasAuthority("VIEWACCOUNT")
                .requestMatchers("/myBalance").hasAnyAuthority("VIEWACCOUNT","VIEWBALANCE")
                .requestMatchers("/myCards").hasAuthority("VIEWCARDS")
                .requestMatchers("/myLoans").hasAuthority("VIEWLOANS")*/
                .requestMatchers("/myAccount").hasRole("USER")
                .requestMatchers("/myBalance").hasAnyRole("ADMIN","USER")
                .requestMatchers("/myCards").hasRole("ADMIN")
                .requestMatchers("/myLoans").hasRole("USER")
                .requestMatchers("/user")
                .authenticated()
                .requestMatchers("/notices", "/contact", "/register").permitAll()
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
   /* @Bean
    public UserDetailsService jdbcUserDetailsService(DataSource ds) {
        return new JdbcUserDetailsManager(ds);
    }*/

    @Bean
    public PasswordEncoder l() {
        return new BCryptPasswordEncoder();
    }
}
