package com.jvi.swat.ms.authorization.service.impl;

import com.jvi.swat.ms.authorization.entity.User;
import com.jvi.swat.ms.authorization.exception.AuthorizationUserAlreadyExistsException;
import com.jvi.swat.ms.authorization.model.UserDTO;
import com.jvi.swat.ms.authorization.repository.UserRepository;
import com.jvi.swat.ms.authorization.service.definition.UserService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Validate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  public void registerUser(final UserDTO userDTO) {

    log.info("Registering username {}", userDTO);

    if (hasUserWithName(userDTO.getUsername())) {
      throw new AuthorizationUserAlreadyExistsException(userDTO.getUsername());
    }

    final String encodedPassword = passwordEncoder.encode(userDTO.getPassword());

    final User user = User.builder()
        .username(userDTO.getUsername())
        .encodedPassword(encodedPassword)
        .roles(userDTO.getRoles())
        .build();

    userRepository.save(user);
  }

  @Override
  public Optional<User> retrieveUser(final String username) {

    Validate.notBlank(username, "username can not be blank");

    return userRepository.findByUsername(username);
  }

  @Override
  public boolean hasUserWithName(final String username) {

    Validate.notBlank(username, "username can not be blank");

    return userRepository.findByUsername(username).isPresent();
  }
}
