package com.devsuperior.bds02.controller.controllerException;

import com.devsuperior.bds02.dto.EventDTO;
import com.devsuperior.bds02.service.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/events")
public class EventController {

    private EventService service;

    public EventController(EventService service) {
        this.service = service;
    }


    @PutMapping("/{id}")
    public ResponseEntity<EventDTO> update(@PathVariable Long id, @RequestBody EventDTO dto) {
        EventDTO entity = service.update(id, dto);
        return ResponseEntity.ok().body(entity);
    }
}
