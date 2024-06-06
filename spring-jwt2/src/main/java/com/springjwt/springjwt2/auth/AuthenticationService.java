package com.springjwt.springjwt2.auth;

import com.springjwt.springjwt2.dao.MailBody;
import com.springjwt.springjwt2.dao.OTPRepository;
import com.springjwt.springjwt2.dao.TokenRepository;
import com.springjwt.springjwt2.dao.UserRepository;
import com.springjwt.springjwt2.entity.Otp;
import com.springjwt.springjwt2.entity.User;
import com.springjwt.springjwt2.exception.NotFoundEmailException;
import com.springjwt.springjwt2.exception.NotFoundVerification;
import com.springjwt.springjwt2.exception.OtpIsExpiredException;
import com.springjwt.springjwt2.exception.TypeNotMatchException;
import com.springjwt.springjwt2.model.request.ChangePasswordRequest;
import com.springjwt.springjwt2.service.EmailService;
import com.springjwt.springjwt2.service.JwtService;
import com.springjwt.springjwt2.service.OtpService;
import com.springjwt.springjwt2.service.UserService;
import com.springjwt.springjwt2.token.Token;
import com.springjwt.springjwt2.token.TokenType;
import com.springjwt.springjwt2.util.EmailUtil;
import com.springjwt.springjwt2.util.OtpUtil;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final TokenRepository tokenRepository;
    private final OtpUtil otpUtil;
    private final EmailUtil emailUtil;
    private final OtpService otpService;
    private final EmailService emailService;
    private final OTPRepository otpRepository;
     private final UserService userService;

    public ResponseEntity<String> register(RegisterRequest request) throws MessagingException {

        // Check if the user already exists
        Optional<User> existingUserOptional = userRepository.findUserByEmail(request.getEmail());
        if (existingUserOptional.isPresent()) {
            // User already exists, redirect to login page
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("User already exists. Please go to the login page.");
        }

        var user= User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                                 .build();
        var savedUser = userRepository.save(user);

        int otp =  otpUtil.generateOtp(); // Implement OTP generation logic
        MailBody  mailBody=MailBody.builder()
                .to(request.getEmail())
                 .text("this is otp for verification")
                .subject("User registered successfully. Please check your email for OTP")
                .build();
       // emailService.sendSimpleMessage(mailBody);

          sendOtp(request.getEmail(),otp);
        saveUserOtp(savedUser,otp);
        // create my token
////        var jwtToken= jwtService.generateToken(savedUser);
////        saveUserToken(user, jwtToken);
////        AuthentectionResponse.builder().token(jwtToken).build()
//        return  ResponseEntity.ok( jwtToken)  ;

      return ResponseEntity.ok("successfully registered go to email for verification");

    }


// make login method
public AuthentectionResponse authenticate(AuthenticationRequest request) throws NotFoundEmailException, NotFoundVerification, TypeNotMatchException {
    try {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
    } catch (AuthenticationException e) {
        // If authentication fails, handle exceptions
        if (e instanceof UsernameNotFoundException) {
            throw new NotFoundEmailException("User not found. Please go to forget password.");
        } else if (e instanceof BadCredentialsException) {
            throw new TypeNotMatchException("The username or password is not correct. Check forget password.");
        } else {
            throw e; // Re-throw other authentication exceptions
        }
    }

    var userOptional = userRepository.findUserByEmail(request.getEmail());

    if (userOptional.isEmpty()) {
        throw new NotFoundEmailException("User not found. Please go to forget password.");
    }

    var user = userOptional.get();

    if (user.isEnabled()) {
        var jwtToken = jwtService.generateToken(user);
        saveUserToken(user, jwtToken);
        return AuthentectionResponse.builder().token(jwtToken).build();
    } else {
        int otp = otpUtil.generateOtp(); // Implement OTP generation logic
        sendOtp(request.getEmail(), otp);
        throw new NotFoundVerification("Please activate email verification.");
    }
}




    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void saveUserOtp(User user, int otp){
        Otp otpEntity =  Otp.builder()
                .otp(otp)
                .expirationTime(LocalTime.now().plusMinutes(5))
                .user(user)
                .build();

         otpEntity.setUser(user);
        otpService.saveOTP(otpEntity);
    }

    public void sendOtp(String email, int otp) {

        try {
            emailService.sendOtpEmail( otp,email);
        } catch (MessagingException e) {
            // Handle the exception appropriately
            e.printStackTrace();
        }
    }


    public ResponseEntity<String> activate(  String email,  int otp) throws OtpIsExpiredException {
        Optional<User> userOptional = userRepository.findUserByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            Optional<Otp> otpOptional = otpRepository.findByOtpAndUserId(otp, user.getId());
            if ( otpUtil.OtpIsExpired(otpOptional.get().getExpirationTime())) {
                otpRepository.deleteById(otpOptional.get().getId());
                throw new OtpIsExpiredException("the otp is expire");
            }
            user.setEnabledd(true);
            userRepository.save(user);
            return ResponseEntity.ok("User activated successfully.");
        }
        return ResponseEntity.status(400).body("Invalid OTP or user not found.");

    }

    public  ResponseEntity<String> regenerateAndSendOtp(String email) throws MessagingException {
        Optional<User> userOptional = userRepository.findUserByEmail(email);

          int otp =  otpUtil.generateOtp(); // Implement OTP generation logic

            saveUserOtp(userOptional.get(),otp);
            sendOtp(userOptional.get().getEmail(),otp);

            return ResponseEntity.ok("go to email for verification");

    }


    public void forgetPassword(String email) throws NotFoundEmailException {


        // get user
        Optional<User> user=userRepository.findUserByEmail(email);
        if(user.isEmpty())
            throw new NotFoundEmailException("this user is not found");
        int otp=otpUtil.generateOtp();
        sendOtp(email,otp);
        saveUserOtp(user.get(),otp);

    }


    public void changePassword(ChangePasswordRequest request) throws NotFoundEmailException, NotFoundVerification {
            userService.changePassword(request);
    }
}
