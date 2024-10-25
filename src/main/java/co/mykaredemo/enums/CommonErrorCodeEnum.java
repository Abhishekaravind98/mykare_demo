package co.mykaredemo.enums;


import co.mykaredemo.exceptions.IErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum CommonErrorCodeEnum implements IErrorCode {

    NOT_MODIFIED(HttpStatus.NOT_MODIFIED, 304, "Not Modified"),
    NO_CONTENT(HttpStatus.NO_CONTENT, 204, "No Content"),

    BAD_REQUEST(HttpStatus.BAD_REQUEST, 400, "Bad Request"),
    NOT_FOUND(HttpStatus.NOT_FOUND, 404, "Not Found"),
    EXPECTATION_FAILED(HttpStatus.EXPECTATION_FAILED, 417, "Expectation Failed"),
    ALREADY_EXIST(HttpStatus.ALREADY_REPORTED, 208, "Already Exist"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, 401, "Unauthorized"),
    SERVICE_UNAVAILABLE(HttpStatus.SERVICE_UNAVAILABLE, 503, "No Content");

    private final HttpStatus httpStatus;

    private final int code;

    private final String message;

    CommonErrorCodeEnum(HttpStatus httpStatus, int code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}
