package com.springjwt.springjwt2.dao;

import com.springjwt.springjwt2.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends   JpaRepository<User,Integer>{

       User  findByEmail(String email);

   Optional<User>  findUserByEmail(String email);
       User findUserById(int id);

       @Transactional
       @Modifying
         @Query("update User u set u.password=?2 where u.email=?1")
       void updatePassword(String email, String password);

}
