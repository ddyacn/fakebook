package smpl.oauth2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;

@EnableAuthorizationServer
@Configuration
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {

  private UserDetailsService userDetailsService;
  private AuthenticationManager authenticationManager;
  private ClientDetailsService clientDetailsService;

  @Autowired
  public void setAuthenticationManager(AuthenticationManager manager) {
    this.authenticationManager = manager;
  }

  @Autowired
  public void setUserDetailsService(UserDetailsService userDetailsService) {
    this.userDetailsService = userDetailsService;
  }

  @Autowired
  public void setClientDetailsService(
      ClientDetailsService clientDetailsService) {
    this.clientDetailsService = clientDetailsService;
  }

  @Override
  public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
    endpoints.userDetailsService(userDetailsService)
        .authenticationManager(authenticationManager)
        .setClientDetailsService(clientDetailsService);
  }
}
