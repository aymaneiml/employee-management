package com.prj.employee_management.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.prj.employee_management.entities.UserAccount;

@Repository
public interface UserAccountRepo extends JpaRepository<UserAccount, UUID>{

    Optional<UserAccount> findOneByUsername(String username);

    @Query("""
        SELECT COUNT(u) > 0 From UserAccount u
        WHERE u.username = :username AND u.employee.id = :employeeId    
        """)
    public boolean isOwner(@Param("username") String username, @Param("employeeId") UUID employeeId);    

}