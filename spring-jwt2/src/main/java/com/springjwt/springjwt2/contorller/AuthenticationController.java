package com.springjwt.springjwt2.contorller;

import com.springjwt.springjwt2.auth.AuthentectionResponse;
import com.springjwt.springjwt2.auth.AuthenticationRequest;
import com.springjwt.springjwt2.auth.AuthenticationService;
import com.springjwt.springjwt2.auth.RegisterRequest;
import com.springjwt.springjwt2.entity.User;
import com.springjwt.springjwt2.exception.NotFoundEmailException;
import com.springjwt.springjwt2.exception.NotFoundVerification;
import com.springjwt.springjwt2.exception.OtpIsExpiredException;
import com.springjwt.springjwt2.exception.TypeNotMatchException;
import com.springjwt.springjwt2.model.request.ChangePasswordRequest;
import com.springjwt.springjwt2.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Tag(name = "Authentication API", description = "API for managing authentication of app")
public class AuthenticationController {  // to create or manage new account and authenticate an existeing user


    private final AuthenticationService service;

    // make api register
    @PostMapping("/register")
    @Operation(
            summary = "test registering of specific user",
            description = "This operation  to check validaty of token of user "
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful request"),
            @ApiResponse(responseCode = "404", description = "Error for client"),
            @ApiResponse(responseCode = "500", description = "Error for server")
    })
    public ResponseEntity<String> register(  // authenticationResponse return a token
                                             @RequestBody RegisterRequest request
    ) throws MessagingException {
      return  service.register(request) ;
    }



    // make api register
    @PostMapping("/authenticate")
    @Operation(
            summary = "test login for specific user",
            description = "This operation  to check validaty  of user name and passwrod and valid token for specific user "
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful request"),
            @ApiResponse(responseCode = "404", description = "Error for client"),
            @ApiResponse(responseCode = "500", description = "Error for server")
    })
    public ResponseEntity<AuthentectionResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) throws NotFoundEmailException, NotFoundVerification, TypeNotMatchException {
        return ResponseEntity.ok(service.authenticate(request));

    }

    @PostMapping("/activate/{otp}/{email}")
    @Operation(
            summary = "  otp with specific email",
            description = "This operation  to check validaty  of specific email and otp "
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful request"),
            @ApiResponse(responseCode = "404", description = "Error for client"),
            @ApiResponse(responseCode = "500", description = "Error for server")
    })
    public ResponseEntity<ResponseEntity<String>> activate(@PathVariable int otp, @PathVariable String email) throws OtpIsExpiredException {



        return  ResponseEntity.ok(service.activate(email,otp));
    }

    @Operation(summary = "Regenerate and send OTP to the specified email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OTP successfully generated and sent"),
            @ApiResponse(responseCode = "400", description = "Invalid email provided"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/regenerateOtp/{email}")    // if otp is expired generate otp
    public ResponseEntity<String> regenerateOtp(@PathVariable String email) {
        try {
            service.regenerateAndSendOtp(email);
            return ResponseEntity.ok("OTP successfully generated and sent to " + email);
        } catch (MessagingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send OTP");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid email provided");
        }
    }


    @GetMapping("/forgetPassword")
    public ResponseEntity<String> forgetPassword(@PathVariable("email") String email) throws NotFoundEmailException {
        service.forgetPassword(email);
        return ResponseEntity.ok("OTP sent to your email forgetPassword fr verification.");
    }


    @PostMapping("/changePassword")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordRequest request) throws NotFoundEmailException, NotFoundVerification {
        service.changePassword(request);
        return ResponseEntity.ok("Password changed successfully.");
    }

}


