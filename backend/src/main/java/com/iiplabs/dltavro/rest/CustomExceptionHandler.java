package com.iiplabs.dltavro.rest;

import java.util.Date;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.iiplabs.dltavro.exceptions.InvalidContentException;
import com.iiplabs.dltavro.model.dto.ErrorDetails;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

@Log4j2
@RestControllerAdvice(annotations = {RestControllerAnnotation.class})
public class CustomExceptionHandler {

    /**
     * Handles invalid content type exceptions
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidContentException.class)
    public final HttpEntity<ErrorDetails> invalidContentExceptions(InvalidContentException e, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails.ErrorDetailsBuilder(e.getMessage())
                .details(request.getDescription(false))
                .timeStamp(new Date())
                .status(HttpStatus.BAD_REQUEST.value())
                .build();
        log.error(e, e);
        log.error(errorDetails.toString());
        return new HttpEntity<>(errorDetails);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestPartException.class)
    public final HttpEntity<ErrorDetails> missingServletRequestPartExceptions(MissingServletRequestPartException e, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails.ErrorDetailsBuilder(e.getMessage())
                .details(request.getDescription(false))
                .timeStamp(new Date())
                .status(HttpStatus.BAD_REQUEST.value())
                .build();
        log.error(e, e);
        log.error(errorDetails.toString());
        return new HttpEntity<>(errorDetails);
    }

    /**
     * Handles all other exceptions
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public final HttpEntity<ErrorDetails> allExceptions(Exception e, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails.ErrorDetailsBuilder(e.getMessage())
                .details(request.getDescription(false))
                .timeStamp(new Date())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .build();
        log.error(e, e);
        log.error(errorDetails.toString());
        return new HttpEntity<>(errorDetails);
    }

}
