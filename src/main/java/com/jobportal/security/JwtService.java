//package com.jobportal.security;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.security.Keys;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import javax.crypto.SecretKey;
//import java.nio.charset.StandardCharsets;
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//
//@Service
//public class JwtService {
//
//    @Value("${jwt.secret}")
//    private String secret;
//
//    @Value("${jwt.expiration-ms}")
//    private long expirationMs;
//
//    private SecretKey getKey() {
//        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
//    }
//
//    public String generateToken(String username, List<String> roles) {
//        return Jwts.builder()
//                .subject(username)
//                .claims(Map.of("roles", roles))
//                .issuedAt(new Date())
//                .expiration(new Date(System.currentTimeMillis() + expirationMs))
//                .signWith(getKey())
//                .compact();
//    }
//
//    public Claims extractAllClaims(String token) {
//        return Jwts.parser().verifyWith(getKey()).build()
//                .parseSignedClaims(token)
//                .getPayload();
//    }
//
//    public String extractUsername(String token) {
//
//        return extractAllClaims(token).getSubject();
//    }
//
//    @SuppressWarnings("unchecked")
//    public List<String> extractRoles(String token) {
//        return extractAllClaims(token).get("roles", List.class);
//    }
//
//    public boolean isTokenValid(String token) {
//        try {
//            extractAllClaims(token);
//            return true;
//        } catch (Exception ex) {
//            return false;
//        }
//    }
//}
//
//
package com.jobportal.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration-ms}")
    private long expirationMs;

    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(String username, List<String> roles) {
        return Jwts.builder()
                .subject(username)
                .claims(Map.of("roles", roles))
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(getKey())
                .compact();
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    @SuppressWarnings("unchecked")
    public List<String> extractRoles(String token) {
        return extractAllClaims(token).get("roles", List.class);
    }

    // 🔥 NEW METHOD (IMPORTANT)
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    // Optional (keep for backward compatibility)
    public boolean isTokenValid(String token) {
        try {
            extractAllClaims(token);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
