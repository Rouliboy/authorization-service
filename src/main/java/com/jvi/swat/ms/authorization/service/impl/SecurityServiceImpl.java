package com.jvi.swat.ms.authorization.service.impl;

import com.jvi.swat.ms.authorization.entity.User;
import com.jvi.swat.ms.authorization.exception.AuthorizationInvalidCredentialsException;
import com.jvi.swat.ms.authorization.exception.AuthorizationUserNotFoundException;
import com.jvi.swat.ms.authorization.model.JwtToken;
import com.jvi.swat.ms.authorization.model.JwtTokenUserProps;
import com.jvi.swat.ms.authorization.service.definition.SecurityService;
import com.jvi.swat.ms.authorization.service.definition.TokenService;
import com.jvi.swat.ms.authorization.service.definition.UserService;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SecurityServiceImpl implements SecurityService {

  private final TokenService tokenService;
  private final UserService userService;
  private final PasswordEncoder passwordEncoder;

  @Override
  public JwtToken generateToken(final String username, final String password) {

    log.info("Generating token for username {}", username);

    final User user = userService.retrieveUser(username)
        .orElseThrow(() -> new AuthorizationUserNotFoundException(username));

    if (!passwordEncoder.matches(password, user.getEncodedPassword())) {
      throw new AuthorizationInvalidCredentialsException();
    }

    final Map<String, Serializable> customAttributes = new HashMap<>();

    return tokenService.generate(JwtTokenUserProps.builder()
        .username(user.getUsername())
        .roles(user.getRoles())
        .customAttributes(customAttributes)
        .build());
  }

}
