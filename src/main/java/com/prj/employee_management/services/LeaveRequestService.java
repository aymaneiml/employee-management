package com.prj.employee_management.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prj.employee_management.dtos.LeaveRequestCreateDTO;
import com.prj.employee_management.entities.Employee;
import com.prj.employee_management.entities.LeaveRequest;
import com.prj.employee_management.repositories.EmployeeRepo;
import com.prj.employee_management.repositories.LeaveRequestRepo;
import com.prj.employee_management.shared.CustomResponseException;

@Service
public class LeaveRequestService {

    @Autowired
    private LeaveRequestRepo leaveRequestRepo;

    @Autowired
    private EmployeeRepo employeeRepo;

    //CreateOne
    public LeaveRequest createOne(LeaveRequestCreateDTO leaveRequest, UUID employeeId){
        LeaveRequest newLeaveRequest = new LeaveRequest();

        //employye by id
        Employee employee =employeeRepo.findById(employeeId)
            .orElseThrow(()-> CustomResponseException.ResourceNotFound("employee not found with id")
            );
            

        newLeaveRequest.setReason(leaveRequest.reason());
        newLeaveRequest.setStatus("PENDING");
        newLeaveRequest.setStartDate(leaveRequest.startDate());
        newLeaveRequest.setEndDate(leaveRequest.endDate());
        newLeaveRequest.setEmployee(employee);

        leaveRequestRepo.save(newLeaveRequest);

        return newLeaveRequest;
    }

    //get ALL leaves by one employee_id
    public List<LeaveRequest> findAllByEmployeeId(UUID employeeId) {
        List<LeaveRequest> leaveRequests = leaveRequestRepo.findAllByEmployeeId(employeeId);
        return leaveRequests;
    }
}