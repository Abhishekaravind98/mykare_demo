package co.mykaredemo.modules.auth.ports.api;

import co.mykaredemo.modules.auth.dtos.AuthToken;
import co.mykaredemo.modules.auth.dtos.UserSession;
import co.mykaredemo.modules.user.entities.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface TokenServicePort {
    UserSession validateTokenWithUser(String token, UserDetails userDetails);

    AuthToken createTokenForUser(UserDetails userDetails, User user);

    String getUsernameFromToken(String token);

    String getUserIdFromToken(String token);
}
