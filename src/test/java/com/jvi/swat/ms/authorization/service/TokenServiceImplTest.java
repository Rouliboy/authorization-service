package com.jvi.swat.ms.authorization.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.jvi.swat.ms.authorization.configuration.JwtTokenConfigurationProperties;
import com.jvi.swat.ms.authorization.configuration.JwtTokenConstants;
import com.jvi.swat.ms.authorization.model.JwtToken;
import com.jvi.swat.ms.authorization.model.JwtTokenUserProps;
import com.jvi.swat.ms.authorization.model.Role;
import com.jvi.swat.ms.authorization.service.impl.TokenServiceImpl;
import com.google.common.collect.Maps;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import java.io.Serializable;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.xml.bind.DatatypeConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TokenServiceImplTest {

  @Mock
  private JwtTokenConfigurationProperties mockJwtTokenConfigurationProperties;

  @InjectMocks
  private TokenServiceImpl instance;

  @BeforeEach
  void setUp() {
    when(mockJwtTokenConfigurationProperties.getDuration()).thenReturn(1L);
    when(mockJwtTokenConfigurationProperties.getDurationUnit()).thenReturn(ChronoUnit.DAYS);
    when(mockJwtTokenConfigurationProperties.getSigningKey()).thenReturn("my-signing-key");
  }

  @Test
  void generate() {

    final JwtTokenUserProps props = JwtTokenUserProps.builder()
        .username("loginA")
        .customAttributes(Maps.newHashMap())
        .roles(Set.of(Role.USER))
        .build();

    final JwtToken jwtToken = instance.generate(props);

    assertThat(jwtToken).isNotNull();
    assertThat(jwtToken.getExpirationDate()).isAfter(Instant.now());

    final String rawToken = jwtToken.getToken();
    assertThat(rawToken).isNotNull();

    final Claims claims = Jwts.parser()
        .setSigningKey(
            DatatypeConverter
                .parseBase64Binary(mockJwtTokenConfigurationProperties.getSigningKey()))
        .parseClaimsJws(rawToken).getBody();

    assertThat(claims.getSubject()).isEqualTo("loginA");
    assertThat(claims.getIssuer()).isEqualTo("SWAT");
    assertThat(claims.getExpiration()).isNotNull();
    assertThat(claims.getAudience()).isEqualTo("Authorized users");
    assertThat((List) claims.get(JwtTokenConstants.ROLES)).containsOnly(Role.USER.toString());
    assertThat(claims.get(JwtTokenConstants.USER_NAME)).isEqualTo("loginA");
    assertThat((Map<String, Serializable>) claims.get(JwtTokenConstants.CUSTOM_ATTRIBUTES))
        .isEmpty();
  }

}