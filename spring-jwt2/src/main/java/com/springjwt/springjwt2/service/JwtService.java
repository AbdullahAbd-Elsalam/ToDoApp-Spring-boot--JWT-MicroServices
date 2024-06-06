package com.springjwt.springjwt2.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService  {

    private static final  String SECRET_KEY="404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";
    public String getExtractUserEmail(String token) {
       return extractClaim(token,Claims::getSubject);


    }



    // generate token by puting user details
    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(),userDetails);
    }
     // to generate token
    public String generateToken(
            Map<String,Object> extraClaims, UserDetails userDetails
    ){
        return Jwts.builder().setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*24))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

  // is token is valid
    public boolean isTokenVaild(String token, UserDetails userDetails){

        final String username=getExtractUserEmail(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    // is token is expired
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // extract expiration
    private Date extractExpiration(String token) {
        return extractClaim(token,Claims::getExpiration);
    }

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
