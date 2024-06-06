package com.toda.controller;

import com.toda.Exception.ResourceNotFoundException;
import com.toda.Exception.response.ItemResponseError;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ItemNotFoundExceptionHandler {

    // handle your exception of product not found
    @ExceptionHandler
    public ResponseEntity<ItemResponseError> ProductError(ResourceNotFoundException exception){

        ItemResponseError responseError=new ItemResponseError();
        responseError.setCode(HttpStatus.NOT_FOUND.value());
        responseError.setMessege(exception.getMessage());
        responseError.setTimeStamp(System.currentTimeMillis());

        return  new ResponseEntity<>(responseError,HttpStatus.NOT_FOUND);

    }


}
