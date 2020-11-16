package com.jvi.swat.ms.authorization.service.definition;

import com.jvi.swat.ms.authorization.entity.User;
import com.jvi.swat.ms.authorization.model.UserDTO;
import java.util.Optional;

public interface UserService {

  void registerUser(UserDTO userDTO);

  Optional<User> retrieveUser(String username);

  boolean hasUserWithName(String username);

}
