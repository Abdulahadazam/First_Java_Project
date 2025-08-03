package com.example.firstproject.service;

import com.example.firstproject.dto.DepartmentDTO;
import com.example.firstproject.model.Department;

public interface DepartmentService {
    Department createDepartment(DepartmentDTO dto);
}
