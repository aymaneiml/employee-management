package com.prj.employee_management.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.prj.employee_management.entities.Department;

@Repository
public interface DepartmentRepo extends JpaRepository<Department, UUID>{

}