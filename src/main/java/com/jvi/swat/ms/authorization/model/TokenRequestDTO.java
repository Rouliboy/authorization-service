package com.jvi.swat.ms.authorization.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString(exclude = "password")
public class TokenRequestDTO {

  @NotNull
  @NotBlank
  private String username;

  @NotNull
  @NotBlank
  private String password;
}
