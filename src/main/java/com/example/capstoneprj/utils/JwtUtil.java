package com.example.capstoneprj.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.context.annotation.Configuration;

import java.util.Base64;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Configuration
public class JwtUtil {
    private final String secret = "qhuy";


    public String generateToken(String subject){
        return Jwts.builder().setIssuer("").setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.HOURS.toHours(24)))
                .signWith(SignatureAlgorithm.HS512, Base64.getEncoder().encode(secret.getBytes())).compact();
    }

    public Claims getClaim(String token){
        return Jwts.parser().setSigningKey(Base64.getEncoder().encode(secret.getBytes())).parseClaimsJws(token).getBody();
    }

    public boolean isValid(String token, String email){
        String getTokenEmail = getClaim(token).getSubject();
        return (email.equals(getTokenEmail) && !isExpiratedToken(token));
    }

    public boolean isExpiratedToken(String token) {
        return getClaim(token).getExpiration().before(new Date(System.currentTimeMillis()));
    }

    public String getSubject(String token){
        return getClaim(token).getSubject();
    }
}
