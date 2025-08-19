package com.prj.employee_management.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prj.employee_management.dtos.DepartmentCreateDTO;
import com.prj.employee_management.entities.Department;
import com.prj.employee_management.repositories.DepartmentRepo;
import com.prj.employee_management.shared.CustomResponseException;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepo departmentRepo;

    //CRUD
    //findOneById

    public Department findOne(UUID departmentId){
        return departmentRepo.findById(departmentId)
            .orElseThrow(()-> CustomResponseException.ResourceNotFound("department  with id " + departmentId + "not found!"));
    }

    //findAll
    public List<Department> findAll(){
        return departmentRepo.findAll();
    }

    //addOne
    public Department createOne(DepartmentCreateDTO department){
        Department newDepartment = new Department();

        newDepartment.setName(department.name());

        departmentRepo.save(newDepartment);

        return newDepartment;
    }

    //update

    //public Department

    //delete
    public void deleteOne(UUID departmentId) {
        departmentRepo.deleteById(departmentId);
    }
}