package com.prj.employee_management.controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prj.employee_management.entities.Employee;
import com.prj.employee_management.shared.CustomResponseException;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    ArrayList<Employee> employees = new ArrayList<>();
    

    @GetMapping
    public ResponseEntity<ArrayList<Employee>> findAll(){
        return new ResponseEntity<ArrayList<Employee>>(employees, HttpStatus.OK);
    }
    


    @GetMapping("/{employeeId}")
    public ResponseEntity<Employee> findOne(@PathVariable UUID employeeId) {
        //nous avons faire Optional car il peut etre null
        Employee employee = this.employees.stream()
            .filter(emp -> emp.getId().equals(employeeId))
            .findFirst()
            .orElseThrow(() -> CustomResponseException.ResourceNotFound(
                "Employee with id " + employeeId + "not found !"
                ));
        
        return new ResponseEntity<Employee>(employee, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Employee> addEmployee(@RequestBody @Valid Employee newEmployee) {
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

        return new ResponseEntity<Employee>(newEmployee, HttpStatus.OK);
    }

    @DeleteMapping("/{employeeId}")
    public ResponseEntity<Void> deleteEmp(@PathVariable UUID employeeId){
        Optional<Employee> employee = this.employees.stream()
                .filter(emp -> emp.getId().equals(employeeId))
                .findFirst();

        if (employee.isPresent()) {
            employees.remove(employee.get().getId());
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    //dans update on envoi l'id et le 
    @PutMapping("{employeeId}")
    public ResponseEntity<Employee> updateOne(@PathVariable UUID employeeId, @RequestBody @Valid Employee employee) {

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
        

        return new ResponseEntity<Employee>(employee, HttpStatus.CREATED);

    }

}