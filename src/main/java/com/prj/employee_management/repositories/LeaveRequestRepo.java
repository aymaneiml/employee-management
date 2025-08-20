package com.prj.employee_management.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.prj.employee_management.entities.LeaveRequest;

@Repository
public interface LeaveRequestRepo extends JpaRepository<LeaveRequest, UUID>{
    List<LeaveRequest> findAllByEmployeeId(UUID employeeId);
}