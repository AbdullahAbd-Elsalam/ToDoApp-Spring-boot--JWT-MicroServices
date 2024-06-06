package com.springjwt.springjwt2.service;

import com.springjwt.springjwt2.dao.TokenRepository;
import com.springjwt.springjwt2.dao.UserRepository;
import com.springjwt.springjwt2.entity.User;
import com.springjwt.springjwt2.exception.NotFoundEmailException;
import com.springjwt.springjwt2.exception.NotFoundVerification;
import com.springjwt.springjwt2.exception.TypeNotMatchException;
import com.springjwt.springjwt2.model.request.ChangePasswordRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService    {

    @Autowired
private     UserRepository userRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public Optional<User> findUserByEmail(String email) {
        return Optional.empty();
    }


    @Transactional
    public void deleteUserByEmail(String email) throws NotFoundEmailException {


        if(email ==null)
        {
            throw new  NotFoundEmailException("should put valid email");
        }
                  User user = userRepository.findByEmail(email);
                 tokenRepository.deleteTokenById(user.getId());
                  userRepository.delete(user);

    }

    @Transactional
    public void updateUserById(int id, User updatedUser) throws TypeNotMatchException {

        if(id<=0 ){
            throw new TypeNotMatchException("should put valid id");
        }
         User user = userRepository.findUserById(id);

            user.setEmail(updatedUser.getEmail());
            user.setPassword(updatedUser.getPassword());
            userRepository.save(user);

    }

    @Transactional
    public void changePassword(ChangePasswordRequest request) throws NotFoundEmailException, NotFoundVerification {
        var userOptional = userRepository.findUserByEmail(request.getEmail());
        if (userOptional.isEmpty()) {
            throw new NotFoundEmailException("User not found.");
        }

        var user = userOptional.get();

        if (user.getOtp().getOtp()==request.getOtp()) {

            user.setPassword(passwordEncoder.encode(request.getNew_password()));
            userRepository.save(user);
        } else {
            throw new NotFoundVerification("Invalid OTP.");
        }
    }

}
