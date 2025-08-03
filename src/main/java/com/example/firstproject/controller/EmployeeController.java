package com.example.firstproject.controller;

import com.example.firstproject.dto.EmployeeDTO;
import com.example.firstproject.model.Department;
import com.example.firstproject.model.Employee;
import com.example.firstproject.repository.DepartmentRepository;
import com.example.firstproject.repository.Employeerepository;
import com.example.firstproject.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.firstproject.dto.AdjustSalaryRequest;


import java.util.Optional;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService service;
    private final Employeerepository employeeRepo;
    private final DepartmentRepository departmentRepo;

    public EmployeeController(EmployeeService service, Employeerepository employeeRepo, DepartmentRepository departmentRepo) {
        this.service = service;
        this.employeeRepo = employeeRepo;
        this.departmentRepo = departmentRepo;
    }

    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody EmployeeDTO dto) {
        Employee createdEmployee = service.createEmployee(dto);
        return new ResponseEntity<>(createdEmployee, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployee(@PathVariable Long id) {
        return employeeRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody EmployeeDTO dto) {
        return employeeRepo.findById(id).map(emp -> {
            Department dept = departmentRepo.findById(dto.departmentId).orElseThrow();
            emp.setName(dto.name);
            emp.setEmail(dto.email);
            emp.setSalary(dto.salary);
            emp.setJoiningDate(dto.joiningDate);
            emp.setDepartment(dept);
            return new ResponseEntity<>(employeeRepo.save(emp), HttpStatus.OK);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        if (!employeeRepo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        employeeRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }


    @PostMapping("/adjust-salary")
    public ResponseEntity<String> adjustSalary(@RequestBody AdjustSalaryRequest request) {
        try {
            service.adjustSalaries(request);
            return ResponseEntity.ok("Salaries adjusted successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

}
