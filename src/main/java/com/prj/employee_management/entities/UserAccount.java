package com.prj.employee_management.entities;

import java.util.Collection;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="user-account")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAccount implements UserDetails{

    @Id
    @GeneratedValue(generator = "UUID")
    @UuidGenerator
    private UUID id;

    @Column(name="username", unique = true, nullable = false, length = 100)
    private String username;

    @Column(name="password", nullable = false, length = 100)
    private String password;

    @Column(name="role", unique = true, nullable = false, length = 20)
    private String role = "USER";

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="employee_id", unique = true, nullable = false)
    private Employee employee;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }
}