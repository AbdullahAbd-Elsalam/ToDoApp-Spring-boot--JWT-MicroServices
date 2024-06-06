package com.springjwt.springjwt2.service;

import com.springjwt.springjwt2.dao.OTPRepository;
import com.springjwt.springjwt2.entity.Otp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OtpService {

    @Autowired
    private OTPRepository otpRepository;

    public Otp saveOTP(Otp otp) {
        return otpRepository.save(otp);
    }



}
