package smpl.oauth2.config;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableAuthorizationServer
@EnableTransactionManagement
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

  @Autowired
  private DataSource dataSource;

  @Autowired
  private UserDetailsService userDetailsService;

  @Autowired
  private AuthenticationManager authenticationManager;

  @Bean
  public JdbcTokenStore tokenStore() {
    return new JdbcTokenStore(dataSource);
  }

  @Bean
  protected AuthorizationCodeServices authorizationCodeServices() {
    return new JdbcAuthorizationCodeServices(dataSource);
  }

  @Bean
  @Primary
  public DefaultTokenServices tokenServices() {
    DefaultTokenServices tokenServices = new DefaultTokenServices();
    tokenServices.setSupportRefreshToken(true);
    tokenServices.setTokenStore(tokenStore());
    return tokenServices;
  }

  @Override
  public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
    endpoints
        .authorizationCodeServices(authorizationCodeServices())
        .approvalStoreDisabled()
        .tokenStore(tokenStore())
        .userDetailsService(userDetailsService)
        .authenticationManager(authenticationManager);
  }

  @Override
  public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
    // @formatter:off
    clients
        .jdbc(dataSource);
//                .withCxlient("idm")
//                .secret("$2a$10$5AgnOtyUvbSUi/NxR8la4Ou3OR3XN324Zvq13s46.2nHW39508exK")
//                .scopes("default")
//                .accessTokenValiditySeconds(15*60)
//                .refreshTokenValiditySeconds(15*60)
//                .autoApprove(true)
//                .authorizedGrantTypes("password", "refresh_token")
//            .and()
//                .withClient("api")
//                .secret("$2a$10$RhcSragSFIS0nBLEQdrlmeKeqn0wwlkMdWPUkGQ/DM3loUh.59aSy")
//                .scopes("default")
//                .accessTokenValiditySeconds(15*60)
//                .refreshTokenValiditySeconds(15*60)
//                .autoApprove(true)
//                .authorizedGrantTypes("password", "refresh_token");
    // @formatter:on
  }

  @Override
  public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
    security.passwordEncoder(new BCryptPasswordEncoder());
  }
}
