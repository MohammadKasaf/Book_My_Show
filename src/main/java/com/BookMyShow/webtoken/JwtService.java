package com.BookMyShow.webtoken;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class JwtService {

    public static final String SECRET="9D2BA31472073C681EF3859823BAE1DFD71833F33CACA04D5A706C1AE5301C3A45E1F5EF5F1FAE7BFE81650072BDEFD52B474B94D652BB37BB94248A05F8A496";
    public static final long VALIDITY= TimeUnit.MINUTES.toMillis(30);

    public String generateToken(UserDetails userDetails) {
        HashMap<String, String> claims = new HashMap<>();
        claims.put("kaashif", "http://bookmyshow.com");

        return Jwts.builder()
                .claims(claims)
                .subject(userDetails.getUsername())
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plusMillis(VALIDITY)))
                .signWith(generateKey())
                .compact();

    }

    public SecretKey generateKey(){

        byte decodeKey[]=Base64.getDecoder().decode(SECRET);
        return Keys.hmacShaKeyFor(decodeKey);
    }

    public String extractUsername(String jwt){

        Claims claims=Jwts.parser()
                .verifyWith(generateKey())
                .build()
                .parseSignedClaims(jwt)
                .getPayload();

        return claims.getSubject();
    }

    private Claims getClaims(String jwt){

        return Jwts.parser()
                .verifyWith(generateKey())
                .build()
                .parseSignedClaims(jwt)
                .getPayload();
    }

    public Boolean isTokenValid(String jwt){

        Claims claims=getClaims(jwt);
        return claims.getExpiration().after(Date.from(Instant.now()));
    }

}
