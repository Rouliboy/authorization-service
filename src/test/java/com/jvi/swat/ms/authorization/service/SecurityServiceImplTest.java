package com.jvi.swat.ms.authorization.service;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.jvi.swat.ms.authorization.entity.User;
import com.jvi.swat.ms.authorization.exception.AuthorizationInvalidCredentialsException;
import com.jvi.swat.ms.authorization.exception.AuthorizationUserNotFoundException;
import com.jvi.swat.ms.authorization.model.JwtTokenUserProps;
import com.jvi.swat.ms.authorization.model.Role;
import com.jvi.swat.ms.authorization.service.definition.SecurityService;
import com.jvi.swat.ms.authorization.service.definition.TokenService;
import com.jvi.swat.ms.authorization.service.definition.UserService;
import com.jvi.swat.ms.authorization.service.impl.SecurityServiceImpl;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@Import(SecurityServiceImpl.class)
class CurrencyServiceImplTest {

  @MockBean
  private TokenService mockTokenService;

  @MockBean
  private UserService mockUserService;

  @MockBean
  private PasswordEncoder mockPasswordEncoder;

  @Autowired
  private SecurityService instance;

  @Test
  void testGenerateExceptionWhenUserNotFound() {
    final String username = "myUser";
    when(mockUserService.retrieveUser(username)).thenReturn(Optional.empty());

    Assertions
        .assertThrows(AuthorizationUserNotFoundException.class, () ->
            instance.generateToken(username, ""));
  }

  @Test
  void testGenerateExceptionWhenPasswordDoesNotMatch() {

    final String username = "myUser";
    final String password = "myPassword";
    final String encodedPassword = "skmsdkfsdkfdksùfk";
    when(mockUserService.retrieveUser(username)).thenReturn(Optional.of(User.builder()
        .encodedPassword(encodedPassword)
        .username(username)
        .build()));

    when(mockPasswordEncoder.matches(password, encodedPassword)).thenReturn(false);

    Assertions
        .assertThrows(AuthorizationInvalidCredentialsException.class, () ->
            instance.generateToken(username, ""));
  }

  @Test
  void testGenerateValidTokenWhenValidInputs() {

    final String username = "myUser";
    final String password = "myPassword";
    final String encodedPassword = "skmsdkfsdkfdksùfk";
    when(mockUserService.retrieveUser(username)).thenReturn(Optional.of(User.builder()
        .encodedPassword(encodedPassword)
        .username(username)
        .roles(Set.of(Role.USER))
        .build()));

    when(mockPasswordEncoder.matches(password, encodedPassword)).thenReturn(true);

    instance.generateToken(username, password);

    verify(mockTokenService).generate(JwtTokenUserProps
        .builder()
        .username(username)
        .roles(Set.of(Role.USER))
        .customAttributes(Map.of())
        .build());
  }
}