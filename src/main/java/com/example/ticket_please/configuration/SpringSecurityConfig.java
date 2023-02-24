package com.example.ticket_please.configuration;

import com.example.ticket_please.services.impl.UserDetailsServiceImpl;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SpringSecurityConfig{

    private final UserDetailsServiceImpl userDetailsService;


    public SpringSecurityConfig(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        return authenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.headers().disable();
        http.authorizeRequests()
                .requestMatchers("/hello").hasRole("USER")
                .requestMatchers("/movies/showForm").hasRole("ADMIN")
                .requestMatchers("/spectacles/showForm").hasRole("ADMIN")
                .requestMatchers("/movies/edit/*").hasRole("ADMIN")
                .requestMatchers("/spectacles/edit/*").hasRole("ADMIN")
                .requestMatchers("/movies/update/*").hasRole("ADMIN")
                .requestMatchers("/spectacles/update/*").hasRole("ADMIN")
                .requestMatchers("/movies/delete/*").hasRole("ADMIN")
                .requestMatchers("/spectacles/delete/*").hasRole("ADMIN")
                .requestMatchers("/movies/add").hasRole("ADMIN")
                .requestMatchers("/spectacles/add").hasRole("ADMIN")
                .requestMatchers("/movies/admin/*/newRepertoire").hasRole("ADMIN")
                .requestMatchers("/console/*").hasRole("ADMIN")
                .requestMatchers("/movies/*/reservation").hasAnyRole("USER", "ADMIN")
                .requestMatchers("/movies/*/reservation/*").hasAnyRole("USER", "ADMIN")
                .requestMatchers("/spectacles/*/reservation").hasAnyRole("USER", "ADMIN")
                .requestMatchers("/spectacles/*/reservation/*").hasAnyRole("USER", "ADMIN")
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .permitAll();

        return http.build();
    }
}
