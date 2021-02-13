package com.github.web.ujszovetseggyulekezet.errorHandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ErrorHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> illegalArgument(){
        ErrorResponse errorResponse = new ErrorResponse("Error: Invalid request.");
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ErrorHandlerException.class)
    public ResponseEntity<ErrorResponse> errorHandlerException(ErrorHandlerException error){
        HttpStatus errorCode = error.getErrorCode();
        String errorMessage = error.getErrorMessage();
        ErrorResponse errorResponse = new ErrorResponse(errorMessage);
        log.error("Error: ", error);
        return new ResponseEntity<>(errorResponse, errorCode);
    }
}
