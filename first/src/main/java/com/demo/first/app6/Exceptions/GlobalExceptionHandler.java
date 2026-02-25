package com.demo.first.app6.Exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


@RestControllerAdvice
//@ControllerAdvice
public class GlobalExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);


//    @ExceptionHandler({IllegalArgumentException.class, NullPointerException.class})
//    public ResponseEntity<Map<String, Object>> handleBothException(Exception  exception){
//        Map<String,Object> errorResponse = new HashMap<>();
//        errorResponse.put("Message", exception.getMessage());
//        // or bhi cheez add kar skte hai like default
//        errorResponse.put("Timestamp: ", LocalDateTime.now());
//        errorResponse.put("Status", HttpStatus.BAD_REQUEST.value());
//        errorResponse.put("Error", "Bad Request");
//
//        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
//
//    }

    // Now suppose api hit krni thi put request ki and usko change krke delete kr diya, then spring boot throws exception "method not allowed".This is in-built.
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Map<String, Object>> handleInBuiltException(Exception  exception){
        Map<String,Object> errorResponse = new HashMap<>();
        errorResponse.put("Message", exception.getMessage());
        // or bhi cheez add kar skte hai like default
        errorResponse.put("Timestamp: ", LocalDateTime.now());
        errorResponse.put("Status", HttpStatus.METHOD_NOT_ALLOWED.value());
        errorResponse.put("Error", "Method allowed nhi hai");

        return new ResponseEntity<>(errorResponse, HttpStatus.METHOD_NOT_ALLOWED);

    }

    // custom + inbuilt both mapped with one method
    @ExceptionHandler({UserNotFoundException.class,IllegalArgumentException.class, NullPointerException.class})
    public ResponseEntity<Map<String, Object>> handleBothException(Exception  exception){

        logger.error("Error when finding user: ",exception);

        Map<String,Object> errorResponse = new HashMap<>();
        errorResponse.put("Message", exception.getMessage());
        // or bhi cheez add kar skte hai like default
        errorResponse.put("Timestamp: ", LocalDateTime.now());
        errorResponse.put("Status", HttpStatus.BAD_REQUEST.value());
        errorResponse.put("Error", "Bad Request");

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);

    }


}
