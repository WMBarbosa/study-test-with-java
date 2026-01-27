package com.devsuperior.bds02.service;

import com.devsuperior.bds02.dto.EventDTO;
import com.devsuperior.bds02.entities.Event;
import com.devsuperior.bds02.repositories.EventRepository;
import com.devsuperior.bds02.service.serviceException.ResourceNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class EventService {

    private EventRepository repository;

    public EventService(EventRepository repository) {
        this.repository = repository;
    }

    public EventDTO update(Long id, EventDTO dto) {
        Event event = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
        event = setUpdateData(event, dto);
        return new EventDTO(repository.save(event));
    }


    public Event setUpdateData(Event entity, EventDTO dto) {
        entity.setName(dto.getName());
        entity.setDate(dto.getDate());
        entity.setUrl(dto.getUrl());
        entity.getCity().setId(dto.getCityId());
        return entity;
    }
}
