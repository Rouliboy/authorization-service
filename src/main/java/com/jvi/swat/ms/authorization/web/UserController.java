package com.jvi.swat.ms.authorization.web;

import com.jvi.swat.ms.authorization.constants.ControllersConstants;
import com.jvi.swat.ms.authorization.model.UserDTO;
import com.jvi.swat.ms.authorization.service.definition.UserService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = ControllersConstants.USERS_ENDPOINT)
@RequiredArgsConstructor
@Slf4j
public class UserController {

  private final UserService userService;

  @PostMapping(value = "register")
  public ResponseEntity<Void> registerUser(
      @Valid @RequestBody final UserDTO userDTO) {

    userService.registerUser(userDTO);
    return ResponseEntity.created(null).build();
  }

}
