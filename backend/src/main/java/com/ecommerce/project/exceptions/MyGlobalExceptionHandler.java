package com.ecommerce.project.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.HashMap;
import java.util.Map;

// This class is the "Emergency Room" for the whole app.
// Whenever a Controller has a problem, it gets sent here.
@RestControllerAdvice
public class MyGlobalExceptionHandler {

    // This specific method is the "Form-Error Specialist."
    // It only runs when someone types the wrong thing in an input field.
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> myMethodArgumentNotValidException(MethodArgumentNotValidException e) {

        // We create a simple list (Map) to hold our "Mistake Report."
        Map<String, String> response = new HashMap<>();

        // 1. Look at the "Giant Pile of Errors" Spring just found.
        // 2. Go through them one by one (forEach).
        e.getBindingResult().getAllErrors().forEach(err -> {

            // Identify WHICH box had the error (e.g., "The Email Box").
            String fieldName = ((FieldError)err).getField();

            // Identify WHAT the error was (e.g., "Must include an @ symbol").
            String message = err.getDefaultMessage();

            // Match them up: "email" -> "Must include an @ symbol"
            response.put(fieldName, message);
        });

        // Send the clean "Mistake Report" back to the user's screen.
        return new ResponseEntity<Map<String,String>>(response,
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> myResourceNotFoundException(ResourceNotFoundException e) {
        String message = e.getMessage();
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(APIException.class)
    public ResponseEntity<String> myAPIException(APIException e) {
        String message = e.getMessage();
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);

    }








}