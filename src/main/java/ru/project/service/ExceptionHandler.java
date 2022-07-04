package ru.project.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.project.model.ClassError;
import ru.project.model.CustomException;
import ru.project.model.ExceptionResponseCode;

@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<ClassError> handleException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ClassError(ExceptionResponseCode.UNKNOWN.getCode(), ex.getMessage()));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(CustomException.class)
    @ResponseBody
    public ResponseEntity<ClassError> handleAppException(CustomException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ClassError(ex.getCode().getCode(), ex.getMessage()));
    }
}
