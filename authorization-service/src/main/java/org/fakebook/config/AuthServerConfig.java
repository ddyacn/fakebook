package org.fakebook.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;

@Configuration
@EnableAuthorizationServer
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {

  private AuthenticationManager authenticationManager;

  @Autowired
  public void setAuthenticationManager(AuthenticationManager manager) {
    this.authenticationManager = manager;
  }

  @Override
  public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

    // authorizedGrantTypes:
    // password, client_credentials, refresh_token, authorization_code, implicit

    clients
        .inMemory()
        .withClient("acc")
        .secret("psw")
        .scopes("default", "user:read", "user:write")
        .authorizedGrantTypes("password", "refresh_token", "authorization_code")
        .autoApprove(true)
        .and().build();
  }

  @Override
  public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
    endpoints.authenticationManager(authenticationManager)
        .approvalStoreDisabled();
  }
}
