package com.maxiflexy.escalaytapplication.repository;

import com.maxiflexy.escalaytapplication.entity.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String name);
}