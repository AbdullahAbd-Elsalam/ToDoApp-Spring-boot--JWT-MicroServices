package com.springjwt.springjwt2.exception;

import lombok.Builder;
import lombok.Data;

 
public class NotFoundVerification extends Exception{

    public NotFoundVerification(String message){
        super(message);
    }
}
