package com.saru.expensetracker.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@Slf4j
public class JwtService {
    private final String SECRET="614E645267556B58703273357638792F413F4428472B4B6250655368566D5971";

    private final int EXPIRE_DURATION=500;
    private Map<String, Object> createClaims(){
        return new HashMap<>();
    }

    public String generateToken(String username) {
        return Jwts.builder()
                .setClaims(createClaims())
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+EXPIRE_DURATION*60*1000))
                .signWith(getSignKey(),SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSignKey() {
        byte[] keyBytes= Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver)  {
        log.info("inside extractClaim");

        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }


    private Claims extractAllClaims(String token)  {
        log.info("inside extractAllClaims");
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

    }


    public String extractUsername(String token)  {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token)  {
        log.info("inside extractExpiration");

        return extractClaim(token, Claims::getExpiration);
    }

    private Boolean isTokenExpired(String token)  {
        log.info("inside isTokenExpired");
        return extractExpiration(token).before(new Date());
    }

    public Boolean validateToken( UserDetails userDetails,String token)  {
        log.info("inside validate token");
        final String username = extractUsername(token);
        log.info(String.format("usernam->%s",username));
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
