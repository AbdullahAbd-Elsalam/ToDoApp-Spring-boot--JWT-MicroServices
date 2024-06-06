package com.springjwt.springjwt2.util;

import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.Random;

@Component
public class OtpUtil {

  public Integer generateOtp() {
    Random random = new Random();
    return random.nextInt(100_000,999_999);


  }
  // Method to check if the given expiration time is before the current time
  public boolean OtpIsExpired(LocalTime expirationTime) {
    return LocalTime.now().isAfter(expirationTime);
  }
}
