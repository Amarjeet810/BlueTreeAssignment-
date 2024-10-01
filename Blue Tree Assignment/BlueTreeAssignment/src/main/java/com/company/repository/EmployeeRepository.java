package com.company.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.company.entity.EmployeeEntity;

public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {

}
