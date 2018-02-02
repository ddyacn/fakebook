package org.fakebook.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "auth")
public class AuthConfigProperties {

  private List<Map<String, String>> users = new ArrayList<>();
  private List<Map<String, String>> clients = new ArrayList<>();

  public List<Map<String, String>> getUsers() {
    return users;
  }

  public List<Map<String, String>> getClients() {
    return clients;
  }
}
