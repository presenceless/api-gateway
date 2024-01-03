package cd.presenceless.apigateway.validator;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class OrgRouteValidator {
    // List of endpoints that are exempted from Gateway verification
    // The Gateway should check if requests to these endpoints include a JWT token
    // TODO: Since the tokens for orgs are pre-generated, we need to block all endpoints except for /eureka
    public static final List<String> openEndpoints = List.of("/eureka");

    public Predicate<ServerHttpRequest> isSecured = (route) -> openEndpoints.stream()
            .noneMatch(url -> route.getURI().getPath().contains(url));
}
