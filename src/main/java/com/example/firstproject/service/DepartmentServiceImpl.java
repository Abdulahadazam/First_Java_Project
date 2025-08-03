package com.example.firstproject.service;

import com.example.firstproject.dto.DepartmentDTO;
import com.example.firstproject.model.Department;
import com.example.firstproject.repository.DepartmentRepository;
import org.springframework.stereotype.Service;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepo;

    public DepartmentServiceImpl(DepartmentRepository departmentRepo) {
        this.departmentRepo = departmentRepo;
    }

    @Override
    public Department createDepartment(DepartmentDTO dto) {
        Department dept = new Department();
        dept.setName(dto.name);
        dept.setCode(dto.code);
        return departmentRepo.save(dept);
    }
}
