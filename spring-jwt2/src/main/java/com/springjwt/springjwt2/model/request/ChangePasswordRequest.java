package com.springjwt.springjwt2.model.request;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordRequest {


    private int otp;
    private String email;

    private String new_password;
}
