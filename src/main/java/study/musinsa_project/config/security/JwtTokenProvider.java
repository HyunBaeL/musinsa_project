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
    private final Key secretKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode("secretKey"));

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

    public boolean validateToken(String jwtToken){
        try {
            Claims claims = Jwts.parser().verifyWith((SecretKey) secretKey).build().parseSignedClaims(jwtToken).getPayload();
            Date now = new Date();
            return claims.getExpiration().after(now);
        } catch (Exception e) {
            return false;
        }
    }

//    public String getUsernameFromToken(String jwtToken){
//        return Jwts.parser()
//                .verifyWith((SecretKey) secretKey)
//                .build()
//                .parseSignedClaims(jwtToken)
//                .getBody()
//                .getSubject();
//    }
}
