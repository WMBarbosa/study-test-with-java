package com.devsuperior.demo.service;

import com.devsuperior.demo.dto.DepartmentDTO;
import com.devsuperior.demo.entities.Department;
import com.devsuperior.demo.repositories.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    private final DepartmentRepository repository;

    public DepartmentService(DepartmentRepository repository) {
        this.repository = repository;
    }

    public List<DepartmentDTO> findAll(){
        List<Department> department = repository.findAllByOrderByNameAsc();
        return department.stream()
                .map(DepartmentDTO::new)
                .toList();
    }
}
