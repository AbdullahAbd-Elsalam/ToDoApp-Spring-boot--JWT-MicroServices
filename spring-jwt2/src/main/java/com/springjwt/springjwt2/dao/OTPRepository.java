package com.springjwt.springjwt2.dao;

import com.springjwt.springjwt2.entity.Otp;
import com.springjwt.springjwt2.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OTPRepository extends JpaRepository<Otp,Integer> {

 void findByOtpAndUserId(int otp, User id);

// @Query("select ot from Otp ot  inner join User us  on ot.user=us.id where ot.otp=:otp and us.id=:id")
 Optional<Otp> findByOtpAndUserId(int otp, int id);
}
