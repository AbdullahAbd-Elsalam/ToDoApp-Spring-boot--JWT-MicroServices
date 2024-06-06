package com.springjwt.springjwt2.contorller;

import com.springjwt.springjwt2.entity.User;
import com.springjwt.springjwt2.exception.NotFoundEmailException;
import com.springjwt.springjwt2.exception.TypeNotMatchException;
import com.springjwt.springjwt2.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user-account")
@Tag(name = "User API", description = "API for managing users")
 public class UserController {

    @Autowired
   private UserService userService;



    @GetMapping("/hi")
    public String getname(){
        return "hello";
    }

    // make delete user
    @DeleteMapping("/user/{email}")
    @Operation(
            summary = "test  deleting user",
            description = "This operation   to make delete for user "
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful request"),
            @ApiResponse(responseCode = "404", description = "Error for client"),
            @ApiResponse(responseCode = "500", description = "Error for server")
    })
    public ResponseEntity<String> deleteUser(@PathVariable("email") String email) throws NotFoundEmailException {

        userService.deleteUserByEmail(email);
      return   ResponseEntity.ok("acount delete successfuly say");
    }



    // make update user
    @PutMapping("/user/{id}")
    @Operation(
            summary = "test  updating user",
            description = "This operation   to make update for user "
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful request"),
            @ApiResponse(responseCode = "404", description = "Error for client"),
            @ApiResponse(responseCode = "500", description = "Error for server")
    })
    public ResponseEntity<String> deleteUser(@PathVariable("id") int id, @RequestBody User userUpdated) throws TypeNotMatchException {

        userService.updateUserById(id,userUpdated);
        return   ResponseEntity.ok("acount  successfuly updated say");
    }


    // check validate token
    @GetMapping("/checkToken")
    public Boolean checkValidateToken(){

        return true;
    }
}
