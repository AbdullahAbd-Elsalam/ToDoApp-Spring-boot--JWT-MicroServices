package com.toda.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
 import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private static final  String SECRET_KEY="404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";
    public String getExtractUserEmail(String token) {
       return extractClaim(token,Claims::getSubject);


    }





  // is token is valid


    // to extract cliam
    public <T> T extractClaim(String token, Function<Claims,T> resolveClaims){

        final Claims claim=extractAllClaims(token);
        return resolveClaims.apply(claim);
    }

    // to extarct all claims
    private Claims extractAllClaims(String token){

        return  Jwts.parserBuilder().
                setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

    }

    // to create signature of token to verefied it
    private Key getSignKey() {
        byte[]keyBytes= Decoders.BASE64.decode(SECRET_KEY);

        return Keys.hmacShaKeyFor(keyBytes);
    }

}
