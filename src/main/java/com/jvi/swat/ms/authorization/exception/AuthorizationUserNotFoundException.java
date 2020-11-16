package com.jvi.swat.ms.authorization.exception;

import java.text.MessageFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class AuthorizationUserNotFoundException extends RuntimeException {

  private static final long serialVersionUID = -2488268838708586753L;

  private static final String MESSAGE = "User with username {0} not found";

  public AuthorizationUserNotFoundException(final String username) {
    super(MessageFormat.format(MESSAGE, username));
  }
}
