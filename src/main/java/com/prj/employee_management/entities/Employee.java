package com.prj.employee_management.entities;

import java.time.LocalDate;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "employee") //pour personaliser le nom de la table, car par defaut prend le nom de la class
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(generator = "UUID")
    @UuidGenerator
    private UUID id;

    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "phone_number", length = 25)
    private String phoneNumber;

    @Column(name = "hire_Date", nullable = false)
    private LocalDate hireDate;

    @Column(name = "position", nullable = false)
    private String position;

    @Column(name = "is_verified",columnDefinition = "BOOLEAN DEFAULT FALSE",nullable = false)
    private boolean isVerified;

    @Column(name = "account_creation_token")
    private String accountCreationToken;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "departement_id", nullable = false)
    @JsonProperty("departmentId")
    private Department department ;

    //faire la redefinition de la methode getDepartment pour retourner juste l'id de departement et non tous l'objet (LAZY)
    public UUID getDepartment(){
        return department.getId();
    }



}