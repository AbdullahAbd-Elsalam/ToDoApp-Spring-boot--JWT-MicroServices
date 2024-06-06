package com.toda.Exception.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemResponseError {

   // this is the response error type
   private int code;
   private String messege;
   private long timeStamp;
}
