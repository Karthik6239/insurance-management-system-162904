package com.example.insurancemanagementbackend.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service handling JWT creation and validation.
 * Uses HS256 with a Base64-encoded secret from environment/configuration.
 */
@Service
public class JwtService {

    @Value("${security.jwt.secret:}")
    private String jwtSecret;

    @Value("${security.jwt.expirationSeconds:36000}")
    private long expirationSeconds;

    private SecretKey getSigningKey() {
        if (jwtSecret == null || jwtSecret.isBlank()) {
            // In production, ensure security.jwt.secret is provided via environment
            // 256-bit base64 value recommended. We fallback to a generated weak key to avoid crash in dev.
            return Keys.hmacShaKeyFor("dev-unsafe-secret-dev-unsafe-secret-dev-unsafe".getBytes());
        }
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // PUBLIC_INTERFACE
    public String generateToken(UserDetails userDetails) {
        Instant now = Instant.now();
        Instant exp = now.plusSeconds(expirationSeconds);
        String roles = userDetails.getAuthorities().stream().map(a -> a.getAuthority()).collect(Collectors.joining(","));

        return Jwts.builder()
            .setSubject(userDetails.getUsername())
            .addClaims(Map.of("roles", roles))
            .setIssuedAt(Date.from(now))
            .setExpiration(Date.from(exp))
            .signWith(getSigningKey(), SignatureAlgorithm.HS256)
            .compact();
    }

    // PUBLIC_INTERFACE
    public String extractUsername(String token) {
        return parse(token).getBody().getSubject();
    }

    // PUBLIC_INTERFACE
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return username.equalsIgnoreCase(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        Date expiration = parse(token).getBody().getExpiration();
        return expiration.before(new Date());
    }

    private Jws<Claims> parse(String token) {
        return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token);
    }
}
