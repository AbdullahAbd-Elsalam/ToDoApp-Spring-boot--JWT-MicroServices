package com.springjwt.springjwt2.entity;

import com.springjwt.springjwt2.entity.User;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.type.descriptor.java.LocalDateJavaType;
import org.springframework.validation.annotation.Validated;

import java.time.LocalTime;
import java.util.Date;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Otp")
@Schema(description = " the details of  otp")
@Validated
public class Otp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = " the unique id of specific otp")
    private int id;

    @Column(nullable = false)
    @Schema(description = "the number of otp")
    private int otp;

    @Column(nullable = false)
//    @Temporal(TemporalType.DATE)
    @Schema(description = "expiration date of otp")
    private LocalTime expirationTime;


    @OneToOne( )
    @JoinColumn(name = "user_id")
     private User user;


}
