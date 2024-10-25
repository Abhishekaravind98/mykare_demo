package co.mykaredemo.modules.auth.services;

import co.mykaredemo.modules.auth.dtos.AuthToken;
import co.mykaredemo.modules.auth.dtos.UserSession;
import co.mykaredemo.modules.auth.enums.Role;
import co.mykaredemo.modules.auth.ports.api.TokenServicePort;
import co.mykaredemo.modules.user.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class TokenServiceImpl implements TokenServicePort {

    private static final String SECRET = "mykarehealthcfa76ef1493rewresd7c1c0ea48fc057a80fcd04a7420f8e8bcd0a7567c27sdf464f6d46df";
    private final long expirationMs = 3600000 * 24 * 5;


    @Override
    public AuthToken createTokenForUser(UserDetails userDetails, User user) {
        Map<String, Object> claims = new HashMap<>();
        String token = createToken(claims, userDetails.getUsername(), user.getId());
        return AuthToken.builder()
                .userId(user.getId())
                .name(user.getName())
                .emailId(user.getEmailId())
                .phoneNumber(user.getPhoneNumber())
                .token(token)
                .role(user.getRole().toString())
                .expiry(extractExpiration(token))
                .build();
    }

    @Override
    public UserSession validateTokenWithUser(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        final String userIdFromToken = getUserIdFromToken(token);
        if (!username.equals(userDetails.getUsername()) && Boolean.TRUE.equals(isTokenExpired(token))) return null;
        return new UserSession(Long.valueOf(userIdFromToken), token, username, Role.valueOf(userDetails.getAuthorities().toArray()[0].toString()));
    }

    @Override
    public String getUsernameFromToken(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public String getUserIdFromToken(String token) {
        return extractClaim(token, Claims::getId);
    }


    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, user.getEmailId(), user.getId());
    }

    private String createToken(Map<String, Object> claims, String subject, Long userId) {
        return Jwts.builder()
                .setClaims(claims)
                .setId(String.valueOf(userId))
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs * 24))
                .signWith(SignatureAlgorithm.HS512, getSigningKey())
                .compact();
    }


    public boolean validateToken(String token) {
        final String username = extractUsername(token);
        return (username != null && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(getSigningKey()).parseClaimsJws(token).getBody();
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
