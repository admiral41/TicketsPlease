package com.example.ticket_please.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.Collection;
import java.util.Collections;

@Entity
@Table(name="users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User implements UserDetails {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name="username",nullable = false)
    @Size(min = 4,message = "Username must have at least 4 characters")
    @Size(max = 50,message = "Username can have a maximum of 50 characters")
    private String username;

    @Column(name = "password", nullable = false)
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;

    @Column(name = "role", nullable = false)
    private String role;

    @Column(name = "email", nullable = false)
    @NotBlank(message = "Enter your e-mail address")
    @Email(message = "Please enter a valid email address")
    private String email;

    @Column(name = "phone", nullable = false)
    @Pattern(regexp = "\\d{10}", message = "Please enter a valid phone number")
    private String phone;

    @Column(name = "name", nullable = false)
    @Size(max = 50, message = "Enter up to 50 characters")
    @NotBlank(message = "Enter your name")
    private String name;

    @Column(name = "surname", nullable = false)
    @Size(max = 50, message = "Enter up to 50 characters")
    @NotBlank(message = "Enter your last name")
    private String surname;

    @Column(name = "active", nullable = false)
    private boolean isEnabled;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
}
