package tesis.backend.backend.security.config;

import java.security.Key;
import java.util.*;
import java.util.function.Function;

import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import tesis.backend.backend.user.entity.User;

@Service
public class JwtService {

    // secret key for digital signature
    @Value("${secret.key}")
    private String SECRET_KEY;
    // Expiration time for token
    @Value("${expiration}")
    private Integer EXPIRATION;

    /*
     Method to extract all of the information in the Token payload
     1. Parse builder
     2. Set my secret signing key to verify the message is not changed
     3. Build
     4. Parse all the claims in the token payload
     5. Get all the claims in the token
    */
    private Claims extractAllClaims(String token) {
        return Jwts
        .parserBuilder()
        .setSigningKey(getSigningKey())
        .build()
        .parseClaimsJws(token)
        .getBody();
    }

    // Method to extract any claim with a Generic method
    public <T> T extractClaim(String token, Function<Claims, T> claimsTFunction) {
        final Claims claims = extractAllClaims(token);
        return claimsTFunction.apply(claims);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String extractUserId(String token) {
        return extractClaim(token, claims -> claims.get("id", String.class));
    }

    public List<String> extractRoles(String token) { 
        return extractClaim(token, claims -> claims.get("roles", List.class)); 
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(User userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(
            Map<String, Object> extraClaims, // extra fields in token payload
            User userDetails
    ) {
        // Get all authorities for a given user to add them in a custom claim
        Set<String> roles = new HashSet<>();
        for(GrantedAuthority authority : userDetails.getAuthorities()) {
            roles.add(authority.getAuthority());
        }
        return Jwts
                .builder()
                .setClaims(extraClaims) // Set custom fields in payload
                .setSubject(userDetails.getUsername()) // Set Token subject
                .claim("id", userDetails.getUserId()) // Custom claim to get subject uuid
                .claim("roles", roles) // Custom role claim
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Boolean isTokenvalid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        // Verify if expiration date is before now, if so is expired
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}
