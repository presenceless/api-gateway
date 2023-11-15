package cd.presenceless.apigateway.filter;


import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouteValidator {
    // List of endpoints that are exempted from Gateway verification
    // The Gateway should check if requests to these endpoints include a JWT token
    public static final List<String> openEndpoints = List.of(
            "/api/v1/auth",
            "/api/v1/auth/gov",
            "/api/v1/auth/attendants",
            "/eureka"
    );

    public Predicate<ServerHttpRequest> isSecured = (route) -> openEndpoints.stream()
            .noneMatch(url -> route.getURI().getPath().contains(url));
}
