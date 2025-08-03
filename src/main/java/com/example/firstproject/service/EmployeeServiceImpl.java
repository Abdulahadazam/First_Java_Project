package com.example.firstproject.service;

import com.example.firstproject.dto.AdjustSalaryRequest;
import com.example.firstproject.dto.EmployeeDTO;
import com.example.firstproject.model.Department;
import com.example.firstproject.model.Employee;
import com.example.firstproject.repository.DepartmentRepository;
import com.example.firstproject.repository.Employeerepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final Employeerepository employeeRepo;
    private final DepartmentRepository departmentRepo;

    private final Map<Long, LocalDateTime> adjustmentTimestamps = new HashMap<>();

    public EmployeeServiceImpl(Employeerepository employeeRepo, DepartmentRepository departmentRepo) {
        this.employeeRepo = employeeRepo;
        this.departmentRepo = departmentRepo;
    }

    @Override
    public Employee createEmployee(EmployeeDTO dto) {
        Department dept = departmentRepo.findById(dto.departmentId).orElseThrow();
        Employee emp = new Employee();
        emp.setName(dto.name);
        emp.setEmail(dto.email);
        emp.setSalary(dto.salary);
        emp.setJoiningDate(dto.joiningDate);
        emp.setDepartment(dept);
        return employeeRepo.save(emp);
    }

    @Override
    public void adjustSalaries(AdjustSalaryRequest request) {
        Long deptId = request.departmentId;
        int score = request.performanceScore;

        LocalDateTime now = LocalDateTime.now();

        if (adjustmentTimestamps.containsKey(deptId)) {
            LocalDateTime lastTime = adjustmentTimestamps.get(deptId);
            if (ChronoUnit.MINUTES.between(lastTime, now) < 30) {
                throw new RuntimeException("Salary already adjusted recently for this department.");
            }
        }

        List<Employee> employees = employeeRepo.findAll().stream()
                .filter(emp -> emp.getDepartment().getId().equals(deptId))
                .toList();

        for (Employee emp : employees) {
            double original = emp.getSalary();
            double newSalary = original;


            if (score >= 90) newSalary *= 1.15;
            else if (score >= 70) newSalary *= 1.10;
            else {
                System.out.println("No increase for " + emp.getName());
                continue;
            }

            long years = ChronoUnit.YEARS.between(emp.getJoiningDate(), LocalDate.now());
            if (years > 5) newSalary *= 1.05;

            newSalary = Math.min(newSalary, 200000);

            emp.setSalary(newSalary);
            employeeRepo.save(emp);
        }

        adjustmentTimestamps.put(deptId, now);
    }
}
