package co.mykaredemo.modules.auth.services;

import co.mykaredemo.exceptions.ErrorCodeEnum;
import co.mykaredemo.exceptions.ServiceException;
import co.mykaredemo.modules.auth.dtos.AuthRequest;
import co.mykaredemo.modules.auth.dtos.AuthToken;
import co.mykaredemo.modules.auth.ports.api.AuthServicePort;
import co.mykaredemo.modules.user.entities.User;
import co.mykaredemo.modules.user.ports.spi.UserPersistencePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthServicePort {

    private final UserPersistencePort userJpaAdapter;
    private final TokenServiceImpl tokenService;

    private final AuthenticationManager authenticationManager;


    @Override
    public AuthToken login(AuthRequest authRequest) {
        User user = userJpaAdapter.findByEmailIdAndStatus(authRequest.getUsername());
        if (user == null) {
            log.error("No User found with email :{}", authRequest.getUsername());
            throw new ServiceException(ErrorCodeEnum.USER_NOT_FOUND_OR_ACTIVE);
        }
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return tokenService.createTokenForUser(userDetails, user);
    }
}
