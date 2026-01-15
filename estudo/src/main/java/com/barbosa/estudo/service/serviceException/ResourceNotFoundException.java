package com.barbosa.estudo.service.serviceException;

public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(Object id) {
        super("Resource id: " + id + ", not found" );
    }
}
