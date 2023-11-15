package cd.presenceless.apigateway.Config;

import org.springframework.stereotype.Service;

@Service
public class JWTService {
    public boolean isTokenValid(String token) {
        return true;
    }
}
