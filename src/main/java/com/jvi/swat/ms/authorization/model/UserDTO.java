package com.jvi.swat.ms.authorization.model;

import java.util.HashSet;
import java.util.Set;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@ToString(exclude = "password")
public class UserDTO {

  @NotNull
  @NotBlank
  private String username;

  @NotNull
  @NotBlank
  @Size(min = 8)
  private String password;

  @NotEmpty
  private Set<Role> roles = new HashSet<>();
}
