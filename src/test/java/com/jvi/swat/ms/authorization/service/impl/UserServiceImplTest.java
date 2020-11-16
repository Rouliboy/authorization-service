package com.jvi.swat.ms.authorization.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.jvi.swat.ms.authorization.entity.User;
import com.jvi.swat.ms.authorization.exception.AuthorizationUserAlreadyExistsException;
import com.jvi.swat.ms.authorization.model.Role;
import com.jvi.swat.ms.authorization.model.UserDTO;
import com.jvi.swat.ms.authorization.repository.UserRepository;
import com.jvi.swat.ms.authorization.service.definition.UserService;
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
@Import(UserServiceImpl.class)
class UserServiceImplTest {

  @MockBean
  private UserRepository mockUserRepository;

  @MockBean
  private PasswordEncoder mockPasswordEncoder;

  @Autowired
  private UserService instance;

  @Test
  void testHasUserWithNameRaiseNPEWhenEmptyUsername() {

    final String username = "  ";

    Assertions
        .assertThrows(IllegalArgumentException.class, () ->
            instance.hasUserWithName(username));
  }

  @Test
  void testHasUserWithNameReturnsFalseWhenRepositoryHasNoUser() {

    final String username = "myUser";
    when(mockUserRepository.findByUsername(username)).thenReturn(Optional.empty());

    assertThat(instance.hasUserWithName(username)).isFalse();
  }

  @Test
  void testHasUserWithNameReturnsTrueWhenRepositoryHasUser() {

    final String username = "myUser";
    final User mockUser = mock((User.class));
    when(mockUserRepository.findByUsername(username)).thenReturn(Optional.of(mockUser));

    assertThat(instance.hasUserWithName(username)).isTrue();
  }

  @Test
  void testRetrieveUserWithNameReturnsUserWhenRepositoryHasUser() {

    final String username = "myUser";
    final User mockUser = mock((User.class));
    when(mockUserRepository.findByUsername(username)).thenReturn(Optional.of(mockUser));

    org.assertj.core.api.Assertions.assertThat(instance.retrieveUser(username)).hasValue(mockUser);
  }

  @Test
  void testRetrieveUserWithNameReturnsEmptyUserWhenRepositoryHasNoUser() {
    final String username = "myUser";

    when(mockUserRepository.findByUsername(username)).thenReturn(Optional.empty());

    org.assertj.core.api.Assertions.assertThat(instance.retrieveUser(username)).isEmpty();
  }

  @Test
  void testRegisterUserWithThrowsExceptionWhenUserAlreadyRegistered() {

    final String username = "myUser";
    final String password = "myPassword";

    final UserDTO userDTO = UserDTO.builder()
        .username(username)
        .password(password)
        .build();

    final User mockUser = mock((User.class));
    when(mockUserRepository.findByUsername(username)).thenReturn(Optional.of(mockUser));

    Assertions
        .assertThrows(AuthorizationUserAlreadyExistsException.class, () ->
            instance.registerUser(userDTO));
  }

  @Test
  void testRegisterUserWhenValid() {

    final String username = "myUser";
    final String password = "myPassword";
    final String encodedPassword = "encodedPassword";
    final Set<Role> roles = Set.of(Role.USER);

    final UserDTO userDTO = UserDTO.builder()
        .username(username)
        .password(password)
        .roles(roles)
        .build();

    when(mockUserRepository.findByUsername(username)).thenReturn(Optional.empty());
    when(mockPasswordEncoder.encode(password)).thenReturn(encodedPassword);

    final User expectedUSer = User.builder()
        .username(username)
        .encodedPassword(encodedPassword)
        .roles(roles)
        .build();

    instance.registerUser(userDTO);

    verify(mockUserRepository).save(expectedUSer);
  }
}