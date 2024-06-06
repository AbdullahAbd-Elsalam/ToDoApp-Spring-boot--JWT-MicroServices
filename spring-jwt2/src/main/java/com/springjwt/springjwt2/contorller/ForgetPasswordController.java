package com.springjwt.springjwt2.contorller;


import com.springjwt.springjwt2.auth.AuthenticationService;
import com.springjwt.springjwt2.exception.NotFoundEmailException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/forgetPassword")
public class ForgetPasswordController {

    private final AuthenticationService service;





}
