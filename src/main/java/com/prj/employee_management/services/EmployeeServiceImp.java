package com.prj.employee_management.services;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prj.employee_management.dtos.EmployeeCreateDTO;
import com.prj.employee_management.dtos.EmployeeUpdateDTO;
import com.prj.employee_management.entities.Department;
import com.prj.employee_management.entities.Employee;
import com.prj.employee_management.repositories.DepartmentRepo;
import com.prj.employee_management.repositories.EmployeeRepo;
import com.prj.employee_management.shared.CustomResponseException;

@Service
public class EmployeeServiceImp{


    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private DepartmentRepo departmentRepo;
    
    public Employee findOne(UUID employeeId) {
        
        Employee employee = employeeRepo.findById(employeeId)
            .orElseThrow(() -> CustomResponseException.ResourceNotFound(
                "Employee with id " + employeeId + "not found !"
                ));

        return employee;
    }
    
    
    public List<Employee> findAll(){
        return employeeRepo.findAll();
    }

    
    public void deleteOne(UUID employeeId){
        Optional<Employee> employee = employeeRepo.findById(employeeId);
        if(employee.isPresent()){
            employeeRepo.deleteById(employeeId);
        }    
    }

    
    public Employee addEmployee(EmployeeCreateDTO newEmployeeDTO) {
        if (newEmployeeDTO == null) {
            throw new IllegalArgumentException("Employee cannot be null");
        }

        // check existing employee by ID
        boolean exists = employeeRepo.findAll().stream()
                .anyMatch(emp -> emp.getEmail().equals(newEmployeeDTO.email()));

        if (exists) {
            throw CustomResponseException.AlreadyExists("user already existe");
        }

        //conversion de DTO a entity
        Employee employee = new Employee();

        Department department = departmentRepo.findById(newEmployeeDTO.departmentId())
        .orElseThrow(()-> CustomResponseException.ResourceNotFound(
            "DEpartment with id: " + newEmployeeDTO.departmentId() + " not found!!"
        ));

        
        employee.setFirstName(newEmployeeDTO.firstName());
        employee.setLastName(newEmployeeDTO.lastName());
        employee.setPosition(newEmployeeDTO.position());
        employee.setHireDate(newEmployeeDTO.hireDate());
        employee.setPhoneNumber(newEmployeeDTO.phoneNumber());
        employee.setEmail(newEmployeeDTO.email());
        employee.setDepartment(department);

        
        employeeRepo.save(employee);

        return employee;

    }

    
    public Employee updateEmployee(UUID employeeId, EmployeeUpdateDTO employee) {
            //trouver l'employee
        Employee existingEmployee = employeeRepo.findById(employeeId)
            .orElseThrow(()-> CustomResponseException.ResourceNotFound("Employee with id " + employeeId + "not found !"));

            existingEmployee.setFirstName(employee.firstName());
            existingEmployee.setLastName(employee.lastName());
            existingEmployee.setPhoneNumber(employee.phoneNumber());
            existingEmployee.setPosition(employee.position());

            employeeRepo.save(existingEmployee);
            return existingEmployee;
    }
}