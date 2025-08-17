package com.prj.employee_management.controllers;


import java.util.ArrayList;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.prj.employee_management.abstracts.EmployeeService;
import com.prj.employee_management.entities.Employee;
import com.prj.employee_management.shared.GlobalResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    ArrayList<Employee> employees = new ArrayList<>();
    
    @Autowired
    private EmployeeService employeeService;
    

    @GetMapping
    public ResponseEntity<GlobalResponse<ArrayList<Employee>>> findAll(){
        ArrayList<Employee> employees = employeeService.findAll();
        return new ResponseEntity<>(new GlobalResponse<>(employees), HttpStatus.OK);
    }
    

    @GetMapping("/{employeeId}")
    public ResponseEntity<GlobalResponse<Employee>> findOne(@PathVariable UUID employeeId) {
        Employee employee = employeeService.findOne(employeeId);
        return new ResponseEntity<>(new GlobalResponse<>(employee), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Employee> addEmployee(@RequestBody @Valid Employee newEmployee) {
        Employee employee = employeeService.addEmployee(newEmployee);
        return new ResponseEntity<Employee>(employee, HttpStatus.OK);
    }

    @DeleteMapping("/{employeeId}")
    public ResponseEntity<Void> deleteEmp(@PathVariable UUID employeeId){
        employeeService.deleteOne(employeeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    //dans update on envoi l'id et le 
    @PutMapping("{employeeId}")
    public ResponseEntity<Employee> updateOne(@PathVariable UUID employeeId, @RequestBody @Valid Employee employee) {
        Employee updatedEmployee = employeeService.updateEmployee(employeeId, employee);
        return new ResponseEntity<Employee>(updatedEmployee, HttpStatus.CREATED);
    }

    @PostMapping("{nbr1}/{nbr2}")
    public ResponseEntity<GlobalResponse<?>> division(@PathVariable int nbr1, @PathVariable int nbr2){
        int resultat = nbr1 / nbr2;
        return new ResponseEntity<>(new GlobalResponse<>(resultat), HttpStatus.OK);
    }


}