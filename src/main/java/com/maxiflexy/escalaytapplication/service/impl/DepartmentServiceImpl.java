package com.maxiflexy.escalaytapplication.service.impl;

import com.maxiflexy.escalaytapplication.entity.model.Department;
import com.maxiflexy.escalaytapplication.exception.UserNotFoundException;
import com.maxiflexy.escalaytapplication.entity.model.Admin;
import com.maxiflexy.escalaytapplication.exception.CustomException;
import com.maxiflexy.escalaytapplication.payload.request.DepartmentRequestDto;
import com.maxiflexy.escalaytapplication.repository.AdminRepository;
import com.maxiflexy.escalaytapplication.repository.DepartmentRepository;
import com.maxiflexy.escalaytapplication.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl  implements DepartmentService {

    private final AdminRepository adminRepository;

    private final DepartmentRepository departmentRepository;


    @Override
    public String createDepartment(DepartmentRequestDto requestDto, String username) {

        Admin admin = adminRepository.findByUsername(username).orElse(null);



        // just to confirm that user exist in the database
        if(admin == null){

            throw new UserNotFoundException("Not an admin");
        }

        // save new  department
        try {
            departmentRepository.save(Department.builder()
                            .departmentCreatedBy(admin)
                            .createdUnder(admin.getId())
                            .department(requestDto.getDepartment())
                    .build());
        } catch (Exception e) {
            throw new CustomException("Something went wrong creating the department");
        }


        return "Department created successfully";
    }

    @Override
    public List<Department> getAllDepartment( String username) {

        Admin admin = adminRepository.findByUsername(username).orElse(null);

        // just to confirm that user exists in the database
        if(admin == null){

            throw new UserNotFoundException("Not an admin");
        }

        return admin.getDepartmentList();
    }



//    @Override
//    public List<User> getAllUserUnderDepartment(Long departmentId) {
//
//          Department department = departmentRepository.findById(departmentId).orElse(null);
//
//
//          // check that the category exist
//          if(department == null){
//
//              throw new DoesNotExistException("Department does not exist");
//          }
//
//
//
//        return department.getUserList();
//    }


}
