package com.towh.identity_service.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.towh.identity_service.dto.request.UserLoginRequest;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;

@Component
public class JwtTokenUtil {
    // JWT secret key
    @Value("${jwt.secret}")
    private SecretKey secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    // Generate a JWT token
    public String generateToken(UserLoginRequest request) {
        SecretKey key = new SecretKeySpec(this.secret.getBytes(StandardCharsets.UTF_8), "AES");
        HashMap<String, Object> claims = new HashMap<>();
        claims.put("username", request.getUsername());
        claims.put("email", request.getEmail());
        claims.put("firstName", request.getFirstName());
        claims.put("lastName", request.getLastName());
        claims.put("dob", request.getDob());

        return Jwts.builder()
                .claims(claims)
                .subject(request.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration * 1000))
                .signWith(key)
                .compact();
    }

    public Boolean validateToken(String token, UserLoginRequest userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().verifyWith(secret).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    private Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }
}