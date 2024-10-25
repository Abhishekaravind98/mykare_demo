package co.mykaredemo.modules.auth.ports.api;

import co.mykaredemo.modules.auth.dtos.AuthRequest;
import co.mykaredemo.modules.auth.dtos.AuthToken;

public interface AuthServicePort {
    AuthToken login(AuthRequest authRequest);
}
