package com.egles121.mngmtsys.advice;

import com.egles121.mngmtsys.exception.ManagementAppException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(ManagementAppException.class)
    public ResponseEntity<String> handleManagementAppException(ManagementAppException managementAppException) {
        return new ResponseEntity<>(managementAppException.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
