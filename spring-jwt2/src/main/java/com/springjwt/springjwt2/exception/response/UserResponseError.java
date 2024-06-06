package com.springjwt.springjwt2.exception.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseError {

   // this is the response error type
   private int code;
   private String messege;
   private long timeStamp;
}
