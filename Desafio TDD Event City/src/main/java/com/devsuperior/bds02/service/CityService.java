package com.devsuperior.bds02.service;

import com.devsuperior.bds02.dto.CityDTO;
import com.devsuperior.bds02.entities.City;
import com.devsuperior.bds02.repositories.CityRepository;
import com.devsuperior.bds02.service.serviceException.DatabaseException;
import com.devsuperior.bds02.service.serviceException.ResourceNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CityService {

    private CityRepository repository;

    public CityService(CityRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<CityDTO> findAll() {
        List<City> list = repository.findAllByOrderByNameAsc();
        return list.stream()
                .map(CityDTO::new)
                .toList();
    }

    @Transactional
    public CityDTO insert(CityDTO dto) {
        City entity = setUpEntity(dto);
        return new CityDTO(entity);
    }

    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException(id);
        }
        try {
            repository.deleteById(id);
            repository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation - City id: " + id);
        }
    }


    public City setUpEntity(CityDTO dto) {
        City entity = new City();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        return repository.save(entity);
    }
}
