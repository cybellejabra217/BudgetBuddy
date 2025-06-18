package org.example.apibudgetbuddyproject.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// exception thrown when a requested resource is not found
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends  RuntimeException{
    private static final long serialVersionUID = 1L;

    //creates a new ResourceNotFoundException with the specified message
    public ResourceNotFoundException(String message){
        super(message);
    }
}