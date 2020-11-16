package com.jvi.swat.ms.authorization.configuration;

import java.time.temporal.ChronoUnit;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "authorization.service.jwt")
@NoArgsConstructor
@Setter
@Getter
public class JwtTokenConfigurationProperties {

  @NotNull
  private String signingKey = "nip^ukpyOL-SIRFt7^z~-^dMj=caPtgTx*xg8+_1uAv5|Av7DnVdOqpXzqLb";

  @NotNull
  private long duration = 1;

  @NotNull
  private ChronoUnit durationUnit = ChronoUnit.DAYS;
}
