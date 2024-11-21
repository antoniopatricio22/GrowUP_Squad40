package com.squad40.compesa.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;



import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtTokenService {
    
    private final SecretKey key;
    private final long expirationMillis;

    public JwtTokenService(@Value("${jwt.secret}") String jwtSecret,
                           @Value("${jwt.expiration}") long expirationMillis) {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.expirationMillis = expirationMillis;
    }

    public String generateToken(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal(); // Adicionando a role ao token 
        String role = userDetails.getAuthorities().iterator().next().getAuthority(); // Obtém a role 
        return Jwts.builder() 
                            .subject(userDetails.getUsername())
                            .claim("role", role) // Adiciona a role ao token 
                            .issuedAt(new Date()) 
                            .expiration(new Date(System.currentTimeMillis() + expirationMillis))  
                            .signWith(key) 
                            .compact();
    }

    
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(key)  // Explicitly set the signing key
                    .build()
                    .parseSignedClaims(token); // Use parseClaimsJws for both validation and claims access
            return true;
        } catch (Exception e) {
            // Log the exception with specific details for debugging
            return false;
        }
    }

    public String getUsernameFromToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    . parseSignedClaims(token)
                    .getPayload();
            return claims.getSubject();
        } catch (Exception e) {
            return null;
        }
    }

    public String getRoleFromToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            return claims.get("role", String.class);
        } catch (Exception e) {
            return null;
        }
    }

}
