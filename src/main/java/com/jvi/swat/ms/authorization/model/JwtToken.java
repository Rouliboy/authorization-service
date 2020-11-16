package com.jvi.swat.ms.authorization.model;

import java.time.Instant;
import java.util.Set;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class JwtToken {

  private final String token;
  private final Instant expirationDate;
  private final Set<Role> roles;
}
