package cd.presenceless.apigateway.Config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;

@Service
public class JWTService {
    @Value("${jwt.secret}")
    private String SECRET;

    public boolean isValidToken(final String extractedToken) {
        Claims claims = Jwts.
                parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(extractedToken)
                .getBody();

        return switch (claims) {
            case null -> false;
            case Exception ignored -> false;
            default -> claims.getSubject().split(" ").length == 3;
        };
    }

    public boolean isValidOrgToken(final String extractedToken) {
        Claims claims = Jwts.
                parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(extractedToken)
                .getBody();

        return switch (claims) {
            case null -> false;
            case Exception ignored -> false;
            default -> claims.getSubject().split(" ").length == 1;
        };
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
