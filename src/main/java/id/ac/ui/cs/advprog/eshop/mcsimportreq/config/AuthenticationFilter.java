package id.ac.ui.cs.advprog.eshop.mcsimportreq.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);

    @Autowired
    private RestTemplate template;

    @Value("${app.mcs.authentication.domain}")
    private String authDomain;

    @Value("${app.mcs.authentication.checktoken.route}")
    private String checkTokenRoute;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        logger.debug("Processing authentication for {}", request.getRequestURI());
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            authHeader = authHeader.substring(7);
            UsernamePasswordAuthenticationToken auth = validateToken(authHeader);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        filterChain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken validateToken(String authHeader) {
        try {
            // REST call to AUTH service
            TokenCheckRequest requestObject = new TokenCheckRequest();
            requestObject.setToken(authHeader);

            HttpEntity<TokenCheckRequest> request = new HttpEntity<>(requestObject);

            ResponseEntity<TokenCheckResponse> response = template.exchange(authDomain + checkTokenRoute, HttpMethod.POST, request, TokenCheckResponse.class);

            TokenCheckResponse responseObject = response.getBody();
            assert responseObject != null;
            if (responseObject.getData() != null) {
                ObjectMapper mapper = new ObjectMapper();
                AuthInfo authInfo = mapper.convertValue(responseObject.getData(), AuthInfo.class);
                List<GrantedAuthority> authorities = authInfo.getAuthorities().stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
                return UsernamePasswordAuthenticationToken.authenticated(
                        authInfo, null, authorities
                );
            }
        } catch (Exception e) {
            logger.error("Error validating token", e);
            throw new RuntimeException("Unauthorized access");
        }
        return null;
    }
}

@Getter
@Setter
class TokenCheckResponse {
    private String message;
    private Object data;
}

@Getter
@Setter
class TokenCheckRequest {
    private String token;
}