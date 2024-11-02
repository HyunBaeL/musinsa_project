package study.musinsa_project.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    private final Key secretKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode("c3VwZXItY29kaW5nLWZvb2JhcjEyMzQ1Njc4OWFhYjM="));
    private final long onehour = 1000L*60*60; // 1시간

    public String createToken(String username){
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + onehour);

        return Jwts.builder()
                .claims()
                .subject(username)
                .expiration(expiryDate)
                .and()
                .signWith(secretKey)
                .compact();
    }

}
