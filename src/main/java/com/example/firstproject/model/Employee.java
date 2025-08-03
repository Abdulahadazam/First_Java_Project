package com.example.firstproject.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    private double salary;

    private LocalDate joiningDate;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public double getSalary() {
        return salary;
    }

    public LocalDate getJoiningDate() {
        return joiningDate;
    }

    public Department getDepartment() {
        return department;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public void setJoiningDate(LocalDate joiningDate) {
        this.joiningDate = joiningDate;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
