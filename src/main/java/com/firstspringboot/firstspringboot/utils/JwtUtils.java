package com.firstspringboot.firstspringboot.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtils {
    private String SECRET_KEY = "TaK+Hav^CHEFsEVfhuR*H#78^*k$VerO";


    public String extractUsername(String token){
        return extractAllClaims(token).getSubject();
    }
    public Date extractExpiration(String token){
        return extractAllClaims(token).getExpiration();
    }

    public String extractClaims(String token , String claim){
        return extractAllClaims(token).get(claim , String.class);
    }

    private Claims extractAllClaims(String token){
        return Jwts.parser()
                .verifyWith(getSignedKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
    private Boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(String username){
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims , username);
    }

    private String createToken(Map<String , Object> claims , String subject){
        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .header().empty().add("typ","JWT")
                .and()
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+1000*60*60))
                .signWith(getSignedKey())
                .compact();

    }

    public Boolean validateToken(String token){
        return !isTokenExpired(token);
    }

    private SecretKey getSignedKey(){
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

}
