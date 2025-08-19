package com.prj.employee_management.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prj.employee_management.dtos.DepartmentCreateDTO;
import com.prj.employee_management.entities.Department;
import com.prj.employee_management.services.DepartmentService;
import com.prj.employee_management.shared.GlobalResponse;

@RestController
@RequestMapping("/departments")
public class DepartmentController {


    @Autowired
    private DepartmentService departmentService;


    @GetMapping("/{departmentId}")
    public ResponseEntity<GlobalResponse<Department>> findOne(@PathVariable UUID departmentId){
        return new ResponseEntity<>(new GlobalResponse<>(departmentService.findOne(departmentId)), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<GlobalResponse<List<Department>>> findAll(){
        List<Department> departments = departmentService.findAll();
        return new ResponseEntity<>(new GlobalResponse<>(departments), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<GlobalResponse<Department>> createOne(@RequestBody DepartmentCreateDTO department) {
        Department newDepartment = departmentService.createOne(department);
        return new ResponseEntity<>(new GlobalResponse<>(newDepartment), HttpStatus.CREATED);
    }

    @DeleteMapping("/{departmentId}")
    public ResponseEntity<Void> deleteDep(@PathVariable UUID departmentId){
        departmentService.deleteOne(departmentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}