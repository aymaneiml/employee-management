package com.prj.employee_management.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.prj.employee_management.entities.UserAccount;

@Repository
public interface UserAccountRepo extends JpaRepository<UserAccount, UUID>{

    Optional<UserAccount> findOneByUsername(String username);
}