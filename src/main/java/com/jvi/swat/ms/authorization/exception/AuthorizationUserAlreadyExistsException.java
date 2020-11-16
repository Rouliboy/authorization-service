package com.jvi.swat.ms.authorization.exception;

import java.text.MessageFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class AuthorizationUserAlreadyExistsException extends RuntimeException {

  private static final long serialVersionUID = -2381945255350120668L;

  private static final String MESSAGE = "User with username {0} already exists";

  public AuthorizationUserAlreadyExistsException(final String username) {
    super(MessageFormat.format(MESSAGE, username));
  }
}
