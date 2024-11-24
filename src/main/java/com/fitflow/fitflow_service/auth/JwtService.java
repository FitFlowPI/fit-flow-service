package com.fitflow.fitflow_service.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${security.jwt.expiration-time}")
    private long jwtExpirationTime;

    private static final String SECRET = "cb07a0f5b87e2409b0ccbfbee1a136f05e1fae4ffbd98b82c9095d8677c51f135b5555e5bb890d243f60614dd42dcb9ceebf5c0697342007dfc2717fd4392f6e7101bf4823d7c64ce2a301c021d57d9867840c1ff54cdd214f7d0310953b53fd3f153f723bda3ee56e3e04ceabf08c08f51a1939a8ff10076f757f5d33e52aee27ade04fc0cff8dc230b07cb019e94124296adce08fa1a76bda746d26c848d08c11de5ef36969aa8a271a4102454de7b1ff902a095564d162428dd05af5ae6558fb08f19edbc41694168932f0cb02d35cea176c49e378058e145b51adc8aaffabf2163ce8dcce660076f10cdf4e2e5334ece9100440bca631c8406241886322a";

    public String generateToken(Map<String, Object> claims, UserDetails userDetails) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationTime))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUserName(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
