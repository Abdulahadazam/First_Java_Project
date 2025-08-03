package com.example.firstproject.controller;

import com.example.firstproject.dto.DepartmentDTO;
import com.example.firstproject.model.Department;
import com.example.firstproject.repository.DepartmentRepository;
import com.example.firstproject.service.DepartmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    private final DepartmentService service;
    private final DepartmentRepository departmentRepo;


    public DepartmentController(DepartmentService service, DepartmentRepository departmentRepo) {
        this.service = service;
        this.departmentRepo = departmentRepo;
    }

    @PostMapping
    public ResponseEntity<Department> createDepartment(@RequestBody DepartmentDTO dto) {
        Department createdDept = service.createDepartment(dto);
        return new ResponseEntity<>(createdDept, HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Department> getDepartment(@PathVariable Long id) {
        return departmentRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PutMapping("/{id}")
    public ResponseEntity<Department> updateDepartment(@PathVariable Long id, @RequestBody DepartmentDTO dto) {
        return departmentRepo.findById(id).map(dept -> {
            dept.setName(dto.name);
            dept.setCode(dto.code);
            return new ResponseEntity<>(departmentRepo.save(dept), HttpStatus.OK);
        }).orElse(ResponseEntity.notFound().build());
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long id) {
        if (!departmentRepo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        departmentRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
