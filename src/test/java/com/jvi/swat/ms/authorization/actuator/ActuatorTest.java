package com.jvi.swat.ms.authorization.actuator;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
    , properties = {"spring.main.allow-bean-definition-overriding=true"})
@Slf4j
public class ActuatorTest {

  @Autowired
  private TestRestTemplate testRestTemplate;

  @Test
  @DisplayName("Should return actuator health values")
  public void testHealth() {
    final ResponseEntity<Map> entity = testRestTemplate.getForEntity("/actuator/health", Map.class);
    assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(entity.getBody()).isNotEmpty();
    assertThat(entity.getBody().get("status")).isEqualTo("UP");
  }

  @Test
  @DisplayName("Should return actuator info values")
  public void testInfo() {
    final ResponseEntity<Map> entity = testRestTemplate.getForEntity("/actuator/info", Map.class);
    assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(entity.getBody()).isNotEmpty();
    assertThat(entity.getBody().get("git")).isNotNull();
  }
}
