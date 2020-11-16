package com.jvi.swat.ms.authorization.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class AuthorizationInvalidCredentialsException extends RuntimeException {

  private static final long serialVersionUID = 5332430291717188433L;

  private static final String MESSAGE = "Invalid credentials (username/password)";

  public AuthorizationInvalidCredentialsException() {
    super(MESSAGE);
  }
}
