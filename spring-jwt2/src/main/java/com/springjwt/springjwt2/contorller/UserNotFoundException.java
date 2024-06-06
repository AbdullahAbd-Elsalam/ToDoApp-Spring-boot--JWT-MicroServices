package com.springjwt.springjwt2.contorller;

import com.springjwt.springjwt2.exception.NotFoundEmailException;
import com.springjwt.springjwt2.exception.NotFoundVerification;
import com.springjwt.springjwt2.exception.OtpIsExpiredException;
import com.springjwt.springjwt2.exception.TypeNotMatchException;
import com.springjwt.springjwt2.exception.response.UserResponseError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserNotFoundException {


    // handle your exception of product not found
    @ExceptionHandler
    public ResponseEntity<UserResponseError> UserError(NotFoundVerification exception){

         UserResponseError responseError=new UserResponseError();
        responseError.setCode(HttpStatus.NOT_FOUND.value());
        responseError.setMessege(exception.getMessage());
        responseError.setTimeStamp(System.currentTimeMillis());

        return  new ResponseEntity<>(responseError,HttpStatus.NOT_FOUND);

    }

    // handle your exception of verify  email
    @ExceptionHandler
    public ResponseEntity<UserResponseError> UserError(NotFoundEmailException exception){

        UserResponseError responseError=new UserResponseError();
        responseError.setCode(HttpStatus.NOT_FOUND.value());
        responseError.setMessege(exception.getMessage());
        responseError.setTimeStamp(System.currentTimeMillis());

        return  new ResponseEntity<>(responseError,HttpStatus.NOT_FOUND);

    }

    // handle your exception of  verify id not found
    @ExceptionHandler
    public ResponseEntity<UserResponseError> UserError(TypeNotMatchException exception){

        UserResponseError responseError=new UserResponseError();
        responseError.setCode(HttpStatus.NOT_FOUND.value());
        responseError.setMessege(exception.getMessage());
        responseError.setTimeStamp(System.currentTimeMillis());

        return  new ResponseEntity<>(responseError,HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler
    public ResponseEntity<UserResponseError> UserError(OtpIsExpiredException exception){

        UserResponseError responseError=new UserResponseError();
        responseError.setCode(HttpStatus.NOT_FOUND.value());
        responseError.setMessege(exception.getMessage());
        responseError.setTimeStamp(System.currentTimeMillis());

        return  new ResponseEntity<>(responseError,HttpStatus.NOT_FOUND);

    }
}
