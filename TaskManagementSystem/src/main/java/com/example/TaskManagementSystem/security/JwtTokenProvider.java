package com.example.TaskManagementSystem.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;


@Component
public class JwtTokenProvider {
    //Tao key bi mat de ki len Token
    //Chi Server giu key nay, key nay de trong application.properties hoac .env
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    //thoi gian het han token 86400000 ms = 1 ngay
    private final long JWT_EXPIRATION = 86400000L;

    //ham tao token tu username
    public String generateToken(String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);

        //Tao chuoi JWT
        return Jwts.builder()
                .setSubject(username) // Luu user vao token
                .setIssuedAt(now) //Thoi diem khoi tao
                .setExpiration(expiryDate) //thoi diem het han
                .signWith(key) //ky ten bang key bi mat
                .compact(); // nen thanh chuoi string

    }
}
