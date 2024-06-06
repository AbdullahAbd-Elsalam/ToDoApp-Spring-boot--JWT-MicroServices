package com.springjwt.springjwt2.token;


import com.springjwt.springjwt2.entity.User;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "token")
@Validated
@Schema(description = " the details of  token")
public class Token {

  @Id
  @GeneratedValue
  @Schema(description = " the unique  id of specific token")
  public Integer id;

  @Column(unique = true)
  @Schema(description = "this is the token which contain all details of user")
  public String token;

  @Enumerated(EnumType.STRING)
  public TokenType tokenType = TokenType.BEARER;


  public boolean revoked;
  @Schema(description = " descripe if token is expire or not ")
  public boolean expired;

  @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.DETACH,CascadeType.REMOVE})
  @JoinColumn(name = "user_id")
  public User user;
}
