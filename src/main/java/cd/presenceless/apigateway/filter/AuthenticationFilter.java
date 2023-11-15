package cd.presenceless.apigateway.filter;

import cd.presenceless.apigateway.Config.JWTService;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {
    private final RouteValidator routeValidator;
    private final JWTService jwtService;

    public AuthenticationFilter(RouteValidator routeValidator, JWTService jwtService) {
        super(Config.class);
        this.routeValidator = routeValidator;
        this.jwtService = jwtService;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            // Put your custom filter here
            if (routeValidator.isSecured.test(exchange.getRequest())) {
                // If the header does not contain the Authorization header, return a 401 Unauthorized response
                // otherwise continue the request
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                    return exchange.getResponse().setComplete();
                }

                String authHeader = Objects.requireNonNull(exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION)).get(0);
                if (!authHeader.startsWith("Bearer ")) {
                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                    return exchange.getResponse().setComplete();
                }

                String token = authHeader.substring(7); // 7 is the length of "Bearer "
                // send a request to the auth service to validate the token
                // if token is invalid, return 401
                // if token is valid, continue the request
                // use webclient to send the request
                // TODO: The chosen implement is to send the token to the auth service to validate it

                // A secure alternative is to use a private key to sign the token
                // and use the public key to verify the token
                // https://auth0.com/blog/implementing-jwt-authentication-on-spring-boot/
                if (!jwtService.isTokenValid(token)) {
                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                    return exchange.getResponse().setComplete();
                }
            }
            return chain.filter(exchange);
        });
    }

    public static class Config {
        // Put the configuration properties
    }
}
