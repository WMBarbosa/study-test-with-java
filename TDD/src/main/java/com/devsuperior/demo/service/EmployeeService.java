package com.devsuperior.demo.service;

import com.devsuperior.demo.dto.EmployeeDTO;
import com.devsuperior.demo.entities.Department;
import com.devsuperior.demo.entities.Employee;
import com.devsuperior.demo.repositories.EmployeeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmployeeService {

    private final EmployeeRepository repository;

    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public Page<EmployeeDTO> findAll(Pageable pageable){
        Page<Employee> entities = repository.findAll(pageable);
        return entities.map(EmployeeDTO::new);
    }

    @Transactional
    public EmployeeDTO save(EmployeeDTO dto){
        Employee entity = setEmployee(dto);
        return new EmployeeDTO(entity);
    }


    public Employee setEmployee(EmployeeDTO dto){
        Employee entity = new Employee();
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setDepartment(new Department(dto.getDepartmentId(), null));
        return repository.save(entity);
    }
}
