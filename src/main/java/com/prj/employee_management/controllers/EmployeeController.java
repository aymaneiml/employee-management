package com.prj.employee_management.controllers;


import java.util.ArrayList;
import java.util.List;
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
import com.prj.employee_management.dtos.EmployeeCreateDTO;
import com.prj.employee_management.dtos.EmployeeUpdateDTO;
import com.prj.employee_management.dtos.LeaveRequestCreateDTO;
import com.prj.employee_management.entities.Employee;
import com.prj.employee_management.entities.LeaveRequest;
import com.prj.employee_management.services.EmployeeServiceImp;
import com.prj.employee_management.services.LeaveRequestService;
import com.prj.employee_management.shared.GlobalResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    
    @Autowired
    private EmployeeServiceImp employeeService;

    @Autowired
    private LeaveRequestService leaveRequestService;
    

    @GetMapping
    public ResponseEntity<GlobalResponse<List<Employee>>> findAll(){
        List<Employee> employees = employeeService.findAll();
        return new ResponseEntity<>(new GlobalResponse<>(employees), HttpStatus.OK);
    }
    

    @GetMapping("/{employeeId}")
    public ResponseEntity<GlobalResponse<Employee>> findOne(@PathVariable UUID employeeId) {
        Employee employee = employeeService.findOne(employeeId);
        return new ResponseEntity<>(new GlobalResponse<>(employee), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Employee> addEmployee(@RequestBody @Valid EmployeeCreateDTO newEmployee) {
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
    public ResponseEntity<Employee> updateOne(@PathVariable UUID employeeId, @RequestBody @Valid EmployeeUpdateDTO employee) {
        Employee updatedEmployee = employeeService.updateEmployee(employeeId, employee);
        return new ResponseEntity<Employee>(updatedEmployee, HttpStatus.CREATED);
    }


    //creat one leave
    @PostMapping("/{employeeId}/leave-requests")
    public ResponseEntity<GlobalResponse<LeaveRequest>> createOne(@RequestBody LeaveRequestCreateDTO leaveRequest, @PathVariable UUID employeeId){
        LeaveRequest newLeaveRequest = leaveRequestService.createOne(leaveRequest, employeeId);
        return new ResponseEntity<>(new GlobalResponse<>(newLeaveRequest), HttpStatus.CREATED);
    }

    @GetMapping("/{employeeId}/leave-requests")
    public ResponseEntity<GlobalResponse<List<LeaveRequest>>> findAllLeavesById(@PathVariable UUID employeeId){
        List<LeaveRequest> leaveRequests = leaveRequestService.findAllByEmployeeId(employeeId);
        return new ResponseEntity<>(new GlobalResponse<>(leaveRequests), HttpStatus.OK);
    }

}