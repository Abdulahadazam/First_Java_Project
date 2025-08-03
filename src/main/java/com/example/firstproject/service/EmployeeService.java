package com.example.firstproject.service;

import com.example.firstproject.dto.EmployeeDTO;
import com.example.firstproject.model.Employee;
import com.example.firstproject.dto.AdjustSalaryRequest;


public interface EmployeeService {
    Employee createEmployee(EmployeeDTO dto);

    void adjustSalaries(AdjustSalaryRequest request);

}
