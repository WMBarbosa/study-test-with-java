package com.devsuperior.bds02.service.serviceException;

public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(Long id) {
        super("Resource not found. Id: " + id );
    }
}
