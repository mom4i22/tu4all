package com.momchil.TU4ALL.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.json.JSONObject;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {
    private final String SECRET_KEY = "secret";


    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String extractUsernameFromPayload(String payload) {
        return new JSONObject((new String(Base64.getUrlDecoder().decode(payload)))).getString("sub");
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public Date extractIssuedAt(String token) {
        return extractClaim(token, Claims::getIssuedAt);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }

    public Boolean validateToken(String jwtToVerify) {
        Claims claims = extractAllClaims(jwtToVerify);
        String jwt = Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();

        return jwt.equals(jwtToVerify) && !isTokenExpired(jwtToVerify);
    }

    public Boolean validateTokenToRefresh(String jwtToVerify) {
        String[] parts = jwtToVerify.split("\\.");
        String payload = new String(Base64.getUrlDecoder().decode(parts[1]));
        String jwt = Jwts.builder().setPayload(payload).signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();

        return jwt.equals(jwtToVerify);
    }
}
