package com.springjwt.springjwt2.exception;

public class OtpIsExpiredException extends Exception{

    public OtpIsExpiredException(String message){
        super(message);
    }
}
