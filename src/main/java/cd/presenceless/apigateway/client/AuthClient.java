package cd.presenceless.apigateway.client;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import reactor.core.publisher.Mono;

@HttpExchange(value = "/api/v1/auth", contentType = MediaType.APPLICATION_JSON_VALUE)
public interface AuthClient {
    /**
     * This method is used to verify if the token is valid
     */
    @GetExchange("/verify")
    Mono<Boolean> verify(@RequestHeader("Authorization") String token);

    /**
     * Organisation
     * This method verifies if the jwt token is valid
     */
    @GetExchange("/orgs/verify")
    Mono<Boolean> verifyOrgs(@RequestHeader("Authorization") String token);
}
