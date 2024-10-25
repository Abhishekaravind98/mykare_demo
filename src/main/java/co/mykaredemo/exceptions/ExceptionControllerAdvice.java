package co.mykaredemo.exceptions;


import co.mykaredemo.dtos.ResponseModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice(basePackages = "com.management")
public class ExceptionControllerAdvice {

    @ResponseBody
    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ResponseModel<String>> serviceException(ServiceException e) {
        return ResponseModel.from(e);
    }

    @ResponseBody
    @ExceptionHandler(ControllerException.class)
    public ResponseEntity<ResponseModel<String>> exception(ControllerException e) {
        return ResponseModel.from(e);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthenticationException.class)
    @ResponseBody
    public ResponseEntity<ResponseModel<String>> handleAuthenticationException(AuthenticationException e) {
        return ResponseModel.from(e);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(BadCredentialsException.class)
    @ResponseBody
    public ResponseEntity<ResponseModel<String>> handleAuthenticationException(BadCredentialsException e) {
        return ResponseModel.from(e);
    }
}
