package com.maxiflexy.escalaytapplication.repository;

import com.maxiflexy.escalaytapplication.entity.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

    List<Department> findByCreatedUnder(Long createdUnder);
}