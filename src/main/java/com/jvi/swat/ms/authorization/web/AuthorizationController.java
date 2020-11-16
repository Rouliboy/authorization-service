package com.jvi.swat.ms.authorization.web;

import static com.jvi.swat.ms.authorization.constants.ControllersConstants.AUTHORIZATION_ENDPOINT;

import com.jvi.swat.ms.authorization.model.JwtToken;
import com.jvi.swat.ms.authorization.model.TokenRequestDTO;
import com.jvi.swat.ms.authorization.service.definition.SecurityService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = AUTHORIZATION_ENDPOINT)
@RequiredArgsConstructor
public class AuthorizationController {

  private final SecurityService securityService;

  @PostMapping(value = "/token", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<JwtToken> generateToken(
      @Valid @RequestBody final TokenRequestDTO tokenRequestDTO) {
    return new ResponseEntity<>(
        securityService
            .generateToken(tokenRequestDTO.getUsername(), tokenRequestDTO.getPassword()),
        HttpStatus.OK);
  }

}
