package org.fakebook.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class SecurityConfig {

  private AuthConfigProperties authConfigProperties;

  @Autowired
  public void setAuthConfigProperties(AuthConfigProperties authConfigProperties) {
    this.authConfigProperties = authConfigProperties;
  }

  @Bean
  public UserDetailsService userDetailsService() {

    InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();

    authConfigProperties.getUsers().forEach(
        user -> manager.createUser(
            User.withUsername(user.get("name"))
                .password(user.get("pass"))
                .roles(splitAndTrim(user.get("roles")))
                .build()));

    return manager;
  }

  @Bean
  ClientDetailsService clientDetailsService() throws Exception {

    InMemoryClientDetailsServiceBuilder builder =
        new InMemoryClientDetailsServiceBuilder();

    authConfigProperties.getClients().forEach(
        client -> builder.withClient(client.get("id"))
            .secret(client.get("secret"))
            .authorities("ROLE_CLIENT")
            .redirectUris(splitAndTrim(client.get("redirect-uris")))
            .scopes(splitAndTrim(client.get("scopes")))
            .autoApprove(Boolean.valueOf(client.get("auto-approve")))
            .authorizedGrantTypes(splitAndTrim(client.get("grant-types"))));

    return builder.build();
  }

  private String[] splitAndTrim(String line) {
    if (line == null || line.isEmpty()) {
      return new String[]{};
    }

    String[] values = line.split(",");

    for (int i = 0; i < values.length; i++) {
      values[i] = values[i].trim();
    }
    return values;
  }
}
