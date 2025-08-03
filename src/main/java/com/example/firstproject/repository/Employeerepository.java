package com.example.firstproject.repository;

import com.example.firstproject.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Employeerepository extends JpaRepository<Employee, Long> {

}
