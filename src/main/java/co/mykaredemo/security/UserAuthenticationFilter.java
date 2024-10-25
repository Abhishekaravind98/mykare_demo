package co.mykaredemo.security;

import co.mykaredemo.exceptions.AuthenticationException;
import co.mykaredemo.modules.auth.dtos.UserSession;
import co.mykaredemo.modules.auth.ports.api.TokenServicePort;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static org.apache.commons.lang3.StringUtils.isEmpty;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserAuthenticationFilter extends OncePerRequestFilter {

    private final TokenServicePort tokenService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("Starting a transaction for request: {}", request.getRequestURI());

        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        // If no authorization header is provided or doesn't start with Bearer, skip filtering
        if (isEmpty(header) || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            // Extract and validate the token
            final String token = header.split(" ")[1].trim();
            String usernameFromToken = tokenService.getUsernameFromToken(token);

            if (usernameFromToken != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // Load user details and validate token
                UserDetails userDetails = userDetailsService.loadUserByUsername(usernameFromToken);
                UserSession session = tokenService.validateTokenWithUser(token, userDetails);

                // If the token is valid, set authentication
                if (session != null) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            session, null, userDetails.getAuthorities()
                    );
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    // Set authentication in the security context
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
            filterChain.doFilter(request, response);

        } catch (ExpiredJwtException | SignatureException | MalformedJwtException e) {
            // JWT-specific exceptions (invalid signature, expired, malformed)
            log.error("JWT error: {}", e.getMessage());
            SecurityContextHolder.clearContext();
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");

        } catch (InternalAuthenticationServiceException e) {
            // Internal errors like database access issues
            log.error("Internal authentication service error: {}", e.getMessage());
            SecurityContextHolder.clearContext();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

        } catch (AuthenticationException e) {
            // General authentication failure
            log.error("Authentication exception: {}", e.getMessage());
            SecurityContextHolder.clearContext();
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication failed");
        }

        log.info("Completing transaction for request: {}", request.getRequestURI());
    }

}
