package co.mykaredemo.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCodeEnum implements IErrorCode {
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, 10100, "User not found"),
    USER_NOT_FOUND_OR_ACTIVE(HttpStatus.BAD_REQUEST, 10110, "User not found or active"),
    USER_EMAIL_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, 10120, "This email already registered"),
    ;

    private final HttpStatus httpStatus;

    private final int code;

    private final String message;

    ErrorCodeEnum(HttpStatus httpStatus, int code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}