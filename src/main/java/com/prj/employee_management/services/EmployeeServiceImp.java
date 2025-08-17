package com.prj.employee_management.services;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.prj.employee_management.abstracts.EmployeeService;
import com.prj.employee_management.entities.Employee;
import com.prj.employee_management.shared.CustomResponseException;

@Service
public class EmployeeServiceImp implements EmployeeService{

    ArrayList<Employee> employees = new ArrayList<>();

    @Override
    public Employee findOne(UUID employeeId) {
        
        Employee employee = this.employees.stream()
            .filter(emp -> emp.getId().equals(employeeId))
            .findFirst()
            .orElseThrow(() -> CustomResponseException.ResourceNotFound(
                "Employee with id " + employeeId + "not found !"
                ));

        return employee;
    }
    
    @Override
    public ArrayList<Employee> findAll(){
        return employees;
    }

    @Override
    public void deleteOne(UUID employeeId){
        Optional<Employee> employee = employees.stream()
            .filter( emp -> emp.getId().equals(employeeId))
            .findFirst();

        if(employee.isPresent()){
            employees.remove(employee);
        }    

    }

    @Override
    public Employee addEmployee(Employee newEmployee) {
        if (newEmployee == null) {
            throw new IllegalArgumentException("Employee cannot be null");
        }

        // check existing employee by ID
        boolean exists = this.employees.stream()
                .anyMatch(emp -> emp.getId().equals(newEmployee.getId()));

        if (exists) {
            throw new RuntimeException("User already exists!");
        }

        // assign new IDs
        newEmployee.setId(UUID.randomUUID());
        newEmployee.setDepartementId(UUID.randomUUID());
        this.employees.add(newEmployee);

        return newEmployee;

    }

    @Override
    public Employee updateEmployee(UUID employeeId, Employee employee) {
            //trouver l'employee
        Employee existingEmployee = this.employees.stream()
            .filter(emp -> emp.getId().equals(employeeId))
            .findFirst()
            .orElseThrow(()-> CustomResponseException.ResourceNotFound("Employee with id " + employeeId + "not found !"));

            existingEmployee.setFirstName(employee.getFirstName());
            existingEmployee.setLastName(employee.getLastName());
            existingEmployee.setEmail(employee.getEmail());
            existingEmployee.setPhoneNumber(employee.getPhoneNumber());
            existingEmployee.setDepartementId(employeeId);
            existingEmployee.setPosition(employee.getPosition());
            existingEmployee.setHireDate(employee.getHireDate());
        
            return existingEmployee;
    
    }
}