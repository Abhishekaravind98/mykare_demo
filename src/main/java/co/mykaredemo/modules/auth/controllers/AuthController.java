package co.mykaredemo.modules.auth.controllers;


import co.mykaredemo.dtos.ResponseModel;
import co.mykaredemo.exceptions.ControllerException;
import co.mykaredemo.modules.auth.dtos.AuthRequest;
import co.mykaredemo.modules.auth.dtos.AuthToken;
import co.mykaredemo.modules.auth.ports.api.AuthServicePort;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "auth", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthController {
    private final AuthServicePort authService;

    @PostMapping("login")
    @PermitAll
    public ResponseModel<AuthToken> login(@RequestBody @Valid AuthRequest authRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("Request contains validation errors");
            throw new ControllerException(bindingResult);
        }
        return ResponseModel.of(authService.login(authRequest));
    }
}
