package com.lubycon.eatitall.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Response;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

  private static final ApiInfo DEFAULT_API_INFO = new ApiInfoBuilder()
      .title("다먹어 API")
      .description("<h2>큐레이션, 식당 데이터 제공 API</h2>")
      .build();

  private static final Set<String> DEFAULT_PRODUCES_AND_CONSUMES = new HashSet<>(
      Arrays.asList("application/json", "application/xml"));

  @Bean
  public Docket api() {

    List<Response> responses = new ArrayList<>();
    responses.add(new ResponseBuilder().code("200").description("OK").build());
    responses.add(new ResponseBuilder().code("404").description("Not Found").build());
    responses.add(new ResponseBuilder().code("500").description("Internal Server Error").build());

    return new Docket(DocumentationType.SWAGGER_2)
        .apiInfo(DEFAULT_API_INFO)
        .produces(DEFAULT_PRODUCES_AND_CONSUMES)
        .consumes(DEFAULT_PRODUCES_AND_CONSUMES)
        .select()
        .apis(RequestHandlerSelectors.basePackage("com.lubycon.eatitall"))
        .paths(PathSelectors.ant("/api/**"))
        .build()
        .useDefaultResponseMessages(false)
        .globalResponses(HttpMethod.GET, responses);
  }
}
