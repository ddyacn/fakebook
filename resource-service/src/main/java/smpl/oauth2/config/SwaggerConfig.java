package smpl.oauth2.config;

import static springfox.documentation.builders.PathSelectors.any;

import java.util.HashSet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

  @Bean
  public Docket mailApi() {

    HashSet<String> mediaTypes = new HashSet<>();
    mediaTypes.add(MediaType.APPLICATION_JSON_VALUE);

    return new Docket(DocumentationType.SWAGGER_2)
        .forCodeGeneration(true)
        .produces(mediaTypes)
        .consumes(mediaTypes)
        .useDefaultResponseMessages(false)
        .groupName("smpl")
        .apiInfo(new ApiInfoBuilder()
            .description("Api Service")
            .version("1.0")
            .build())
        .select()
        .apis(RequestHandlerSelectors.basePackage("smpl.oauth2.controller"))
//            .apis(RequestHandlerSelectors.any())
        .paths(any())
        .build();
  }
}
