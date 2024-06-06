package com.springjwt.springjwt2.dao;

import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MailBody {

    private String to;
    private String subject;
    private String text;
}
