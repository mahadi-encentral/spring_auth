package com.mamt4real.authentication.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class DefaultExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ AuthenticationException.class })
    @ResponseBody
    public ResponseEntity<AuthErrorResponse> handleAuthenticationException(Exception ex) {
        AuthErrorResponse re = new AuthErrorResponse(HttpStatus.UNAUTHORIZED.name(), "Authentication Failed", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(re);
    }
}