package com.prj.employee_management.abstracts;

import java.util.ArrayList;
import java.util.UUID;

import com.prj.employee_management.entities.Employee;


public interface EmployeeService {
    Employee findOne(UUID employeeId);
    ArrayList<Employee> findAll();
    void deleteOne(UUID employeeId);
    Employee addEmployee(Employee employee);
    Employee updateEmployee(UUID employeeId, Employee employee);
}