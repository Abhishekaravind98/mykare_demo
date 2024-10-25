package co.mykaredemo.exceptions;

import org.springframework.http.HttpStatus;

public interface IErrorCode {
    HttpStatus getHttpStatus();

    int getCode();

    String getMessage();
}
