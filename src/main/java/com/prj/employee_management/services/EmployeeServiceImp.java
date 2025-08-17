package com.prj.employee_management.services;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.prj.employee_management.abstracts.EmployeeService;
import com.prj.employee_management.dtos.EmployeeCreateDTO;
import com.prj.employee_management.dtos.EmployeeUpdateDTO;
import com.prj.employee_management.entities.Employee;
import com.prj.employee_management.shared.CustomResponseException;

@Service
public class EmployeeServiceImp{

    ArrayList<Employee> employees = new ArrayList<>();

    
    public Employee findOne(UUID employeeId) {
        
        Employee employee = this.employees.stream()
            .filter(emp -> emp.getId().equals(employeeId))
            .findFirst()
            .orElseThrow(() -> CustomResponseException.ResourceNotFound(
                "Employee with id " + employeeId + "not found !"
                ));

        return employee;
    }
    
    
    public ArrayList<Employee> findAll(){
        return employees;
    }

    
    public void deleteOne(UUID employeeId){
        Optional<Employee> employee = employees.stream()
            .filter( emp -> emp.getId().equals(employeeId))
            .findFirst();

        if(employee.isPresent()){
            employees.remove(employee);
        }    

    }

    
    public Employee addEmployee(EmployeeCreateDTO newEmployeeDTO) {
        if (newEmployeeDTO == null) {
            throw new IllegalArgumentException("Employee cannot be null");
        }

        // check existing employee by ID
        boolean exists = this.employees.stream()
                .anyMatch(emp -> emp.getEmail().equals(newEmployeeDTO.email()));

        if (exists) {
            throw new RuntimeException("User already exists!");
        }

        //conversion de DTO a entity
        Employee employee = new Employee();
        // assign new IDs
        employee.setId(UUID.randomUUID());
        employee.setDepartementId(UUID.randomUUID());
        employee.setFirstName(newEmployeeDTO.firstName());
        employee.setLastName(newEmployeeDTO.lastName());
        employee.setPosition(newEmployeeDTO.position());
        employee.setHireDate(newEmployeeDTO.hireDate());
        employee.setPhoneNumber(newEmployeeDTO.phoneNumber());
        employee.setEmail(newEmployeeDTO.email());

        employees.add(employee);

        return employee;

    }

    
    public Employee updateEmployee(UUID employeeId, EmployeeUpdateDTO employee) {
            //trouver l'employee
        Employee existingEmployee = this.employees.stream()
            .filter(emp -> emp.getId().equals(employeeId))
            .findFirst()
            .orElseThrow(()-> CustomResponseException.ResourceNotFound("Employee with id " + employeeId + "not found !"));

            existingEmployee.setFirstName(employee.firstName());
            existingEmployee.setLastName(employee.lastName());
            existingEmployee.setPhoneNumber(employee.phoneNumber());
            existingEmployee.setPosition(employee.position());

        
            return existingEmployee;
    
    }
}