package com.jvi.swat.ms.authorization.service.impl;

import static com.jvi.swat.ms.authorization.configuration.JwtTokenConstants.CUSTOM_ATTRIBUTES;
import static com.jvi.swat.ms.authorization.configuration.JwtTokenConstants.ROLES;
import static com.jvi.swat.ms.authorization.configuration.JwtTokenConstants.USER_NAME;

import com.jvi.swat.ms.authorization.configuration.JwtTokenConfigurationProperties;
import com.jvi.swat.ms.authorization.model.JwtToken;
import com.jvi.swat.ms.authorization.model.JwtTokenUserProps;
import com.jvi.swat.ms.authorization.model.Role;
import com.jvi.swat.ms.authorization.service.definition.TokenService;
import com.google.common.collect.Maps;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

  private final JwtTokenConfigurationProperties jwtTokenConfigurationProperties;

  @Override
  public JwtToken generate(final JwtTokenUserProps jwtTokenUserProps) {

    final Instant now = Instant.now();
    final Instant expirationDate = now
        .plus(Duration.of(jwtTokenConfigurationProperties.getDuration(),
            jwtTokenConfigurationProperties.getDurationUnit()));

    final List<String> roles = jwtTokenUserProps.getRoles().stream().map(Role::toString).collect(
        Collectors.toUnmodifiableList());
    final Map<String, Object> claims = Maps.newHashMap();
    claims.put(ROLES, roles);
    claims.put(USER_NAME, jwtTokenUserProps.getUsername());
    claims.put(CUSTOM_ATTRIBUTES, jwtTokenUserProps.getCustomAttributes());

    final String token = Jwts.builder()
        .setClaims(claims)
        .setSubject(jwtTokenUserProps.getUsername())
        .setIssuedAt(Date.from(now))
        .setIssuer("SWAT")
        .setAudience("Authorized users")
        .setExpiration(Date.from(expirationDate))
        .signWith(SignatureAlgorithm.HS512, jwtTokenConfigurationProperties.getSigningKey())
        .compact();

    return new JwtToken(token, expirationDate, jwtTokenUserProps.getRoles());
  }
}
