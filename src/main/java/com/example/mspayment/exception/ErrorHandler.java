package com.example.mspayment.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.example.mspayment.model.constant.ExceptionConstants.UNEXPECTED_EXCEPTION_CODE;
import static com.example.mspayment.model.constant.ExceptionConstants.UNEXPECTED_EXCEPTION_MESSAGE;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@RestControllerAdvice
public class ExceptionHandler {



    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @
    public ExceptionResponse handleInternal (Exception exception) {
        log.error("Exception:", exception);
        return new ExceptionResponse(UNEXPECTED_EXCEPTION_CODE, UNEXPECTED_EXCEPTION_MESSAGE);
    }

}
