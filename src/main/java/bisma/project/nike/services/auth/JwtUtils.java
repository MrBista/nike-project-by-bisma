package bisma.project.nike.services.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtils {

    @Value("${bisma.app.jwtSecret}")
    private String jwtSecretKey;

    @Value("${bisma.app.jwtExpirationMs}")
    private int jwtExpiredDate;

    public String generateJwtToken(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Date now = new Date();
        Date expiredAt = new Date(now.getTime() + jwtExpiredDate);
        Map<String, Object> detailObj = new HashMap<>();
        detailObj.put("username", userDetails.getUsername());
        detailObj.put("email", userDetails.getEmail());
        detailObj.put("id", userDetails.getId());
        ObjectMapper objectMapper = new ObjectMapper();
        return Jwts
                .builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(now)
                .setExpiration(expiredAt)
                .addClaims(detailObj)
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecretKey));
    }

    public String decodeJwt(String accessToken) {
        return Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(accessToken)
                .getBody()
                .getSubject();
    }
}
