package com.jvi.swat.ms.authorization.model;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder
@EqualsAndHashCode
@RequiredArgsConstructor
@Getter
public class JwtTokenUserProps {

  private final String username;
  private final Set<Role> roles;
  private final Map<String, Serializable> customAttributes;
}
