package com.maxiflexy.escalaytapplication.service;

import com.maxiflexy.escalaytapplication.entity.model.Department;
import com.maxiflexy.escalaytapplication.payload.request.DepartmentRequestDto;

import java.util.List;

public interface DepartmentService {

    //create Department
    String createDepartment(DepartmentRequestDto requestDto, String username);

    // get all departments
    List<Department> getAllDepartment(String username);

   // List<User> getAllUserUnderDepartment(Long departmentId);

}
