package com.jvi.swat.ms.authorization.configuration;

import com.jvi.swat.ms.authorization.AuthorizationApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ClassUtils;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2).select()
        .apis(RequestHandlerSelectors.basePackage(ClassUtils.getPackageName(
            AuthorizationApplication.class))).build()
        .apiInfo(getApiInfo());
  }

  public ApiInfo getApiInfo() {
    return new ApiInfoBuilder().title("JVI - Authorization service").build();
  }

}
