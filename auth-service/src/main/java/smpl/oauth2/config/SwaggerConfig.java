package smpl.oauth2.config;

import static java.util.Collections.singletonList;
import static springfox.documentation.builders.PathSelectors.any;
import static springfox.documentation.builders.RequestHandlerSelectors.basePackage;

import com.google.common.base.Predicate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.AuthorizationScopeBuilder;
import springfox.documentation.builders.ImplicitGrantBuilder;
import springfox.documentation.builders.OAuthBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.LoginEndpoint;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

  @Bean
  public Docket mailApi() {
    HashSet<String> mediaTypes = new HashSet<>();
    mediaTypes.add(MediaType.APPLICATION_JSON_VALUE);

    Predicate<RequestHandler> apis = basePackage("smpl.oauth2.controller");
//        apis = RequestHandlerSelectors.any();

    AuthorizationScope[] authScopes = new AuthorizationScope[1];
    authScopes[0] = new AuthorizationScopeBuilder()
        .scope("read")
        .description("read access")
        .build();
    SecurityReference securityReference = SecurityReference.builder()
        .reference("test")
        .scopes(authScopes)
        .build();

    List<SecurityContext> securityContexts =
        Arrays.asList(SecurityContext.builder()
            .securityReferences(singletonList(securityReference))
            .build(), securityContext());

    return new Docket(DocumentationType.SWAGGER_2)
        .forCodeGeneration(true)
        .produces(mediaTypes)
        .securityContexts(securityContexts)
        .securitySchemes(singletonList(new OAuthBuilder()
            .name("idm_auth")
            .grantTypes(singletonList(new ImplicitGrantBuilder()
                .loginEndpoint(
                    new LoginEndpoint(
                        "http://localhost:8080/oauth/dialog"))
                .build()))
            .scopes(singletonList(
                new AuthorizationScope("write:pets", "modify pets in your account")))
            .build()))
        .consumes(mediaTypes)
        .useDefaultResponseMessages(false)
        .groupName("smpl")
        .apiInfo(new ApiInfoBuilder()
            .description("Identity Manager")
            .version("1.0")
            .build())
        .select()
        .apis(apis)
        .paths(any())

        .build();
  }

  private SecurityContext securityContext() {
    return SecurityContext.builder()
        .securityReferences(defaultAuth())
        .forPaths(PathSelectors.regex("/anyPath.*"))
        .build();
  }

  List<SecurityReference> defaultAuth() {
    AuthorizationScope authorizationScope
        = new AuthorizationScope("global", "accessEverything");

    AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
    authorizationScopes[0] = authorizationScope;

    return Arrays.asList(
        new SecurityReference("mykey", authorizationScopes));
  }
}
