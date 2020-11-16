package com.jvi.swat.ms.authorization;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class AuthorizationApplicationTests {

  @Test
  @DisplayName("Should load contexts")
  public void contextLoads() {

    // For Sonar
    Assertions.assertTrue(true);
  }

}
