package com.jvi.swat.ms.authorization.web;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.jvi.swat.ms.authorization.model.JwtToken;
import com.jvi.swat.ms.authorization.model.Role;
import com.jvi.swat.ms.authorization.model.UserDTO;
import com.jvi.swat.ms.authorization.service.definition.SecurityService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jvi.swat.ms.authorization.constants.ControllersConstants;
import java.time.Instant;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest({AuthorizationController.class})
public class AuthorizationControllerTest {

  @Autowired
  protected MockMvc mvc;

  @MockBean
  private SecurityService mockSecurityService;

  @Test
  @DisplayName("Should generate 400 error with null username")
  public void shouldGenerate400WithNullUser() throws Exception {

    mvc.perform(post(ControllersConstants.AUTHORIZATION_ENDPOINT + "/token")
        .content(asJsonString(UserDTO.builder()
            .username(null)
            .password("myPassword")
            .roles(Set.of(Role.USER))
            .build()
        ))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
        .andReturn();
  }

  @Test
  @DisplayName("Should generate 400 error with null password")
  public void shouldGenerate400WithNullPassword() throws Exception {

    mvc.perform(post(ControllersConstants.AUTHORIZATION_ENDPOINT + "/token")
        .content(asJsonString(UserDTO.builder()
            .username("user")
            .password(null)
            .roles(Set.of(Role.USER))
            .build()
        )).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
        .andReturn();
  }

  @Test
  @DisplayName("Should generate 400 error with blank username")
  public void shouldGenerate400WithBlankUser() throws Exception {

    mvc.perform(post(ControllersConstants.AUTHORIZATION_ENDPOINT + "/token")
        .content(asJsonString(UserDTO.builder()
            .username("  ")
            .password("myPassword")
            .roles(Set.of(Role.USER))
            .build()
        )).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
        .andReturn();
  }

  @Test
  @DisplayName("Should generate 400 error with blank password")
  public void shouldGenerate400WithBlankPassword() throws Exception {

    mvc.perform(post(ControllersConstants.AUTHORIZATION_ENDPOINT + "/token")
        .content(asJsonString(UserDTO.builder()
            .username("user")
            .password("  ")
            .roles(Set.of(Role.USER))
            .build()))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
        .andReturn();
  }

  @Test
  @DisplayName("Should generate valid token with valid username and password")
  public void shouldGenerateValidTokenWithValidCredentials() throws Exception {

    final Instant expirationDate = Instant.now();
    final String expectedExpiration = expirationDate.toString();
    final String fakeToken = "this-is-a-fake-token";
    final String username = "myUser";
    final String password = "myPassword";
    final JwtToken token = new JwtToken(fakeToken, expirationDate, Set.of(Role.USER));
    when(mockSecurityService.generateToken(username, password))
        .thenReturn(token);

    mvc.perform(post(ControllersConstants.AUTHORIZATION_ENDPOINT + "/token")
        .content(asJsonString(UserDTO.builder()
            .username(username)
            .password(password)
            .roles(Set.of(Role.USER))
            .build())).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is(HttpStatus.OK.value()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.token").value(fakeToken))
        .andExpect(MockMvcResultMatchers.jsonPath("$.expirationDate").value(expectedExpiration))
        .andReturn();
  }

  private String asJsonString(final Object obj) throws JsonProcessingException {
    return new ObjectMapper().writeValueAsString(obj);
  }
}
