package com.syg.webfluxdemo.exceptionHandler;

import com.syg.webfluxdemo.dto.InputFailedValidationResponse;
import com.syg.webfluxdemo.exception.InputValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class InputValidationHandler {

    @ExceptionHandler(InputValidationException.class)
    public ResponseEntity<InputFailedValidationResponse> handleException(InputValidationException ex) {
        InputFailedValidationResponse inputFailedValidationResponse = new InputFailedValidationResponse();
        inputFailedValidationResponse.setErrorCode(ex.getErrorCode());
        inputFailedValidationResponse.setInput(ex.getInput());
        inputFailedValidationResponse.setMessage(ex.getMessage());
        return ResponseEntity.badRequest().body(inputFailedValidationResponse);
    }

}
