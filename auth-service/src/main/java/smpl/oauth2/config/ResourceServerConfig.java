package smpl.oauth2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

  @Override
  public void configure(HttpSecurity http) throws Exception {
    // @formatter:off
    http.sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .headers().frameOptions().disable() // for h2 console
        .and()
        //.anonymous().disable()
        .csrf().disable()
        .authorizeRequests()
        .antMatchers("/oauth/token", "/oauth2-token").permitAll()
        .antMatchers("/swagger-ui.html", "/swagger-resources/**",
            "/v2/api-docs", "/resources/**", "/oauth/dialog").permitAll()
        .antMatchers("/current-user").permitAll()
        .antMatchers("/h2/**").permitAll()
        .antMatchers("/users/**", "/clients/**").permitAll()
        .anyRequest().authenticated();
    // @formatter:on
  }

  @Bean
  public DaoAuthenticationProvider authProvider(UserDetailsService userDetailsService) {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(userDetailsService);
    authProvider.setPasswordEncoder(new BCryptPasswordEncoder());
    return authProvider;
  }
}
