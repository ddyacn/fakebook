package smpl.oauth2.auth;

import org.slf4j.Logger;
import smpl.oauth2.model.User;
import smpl.oauth2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomDetailsService implements UserDetailsService {

  @Autowired
  private Logger logger;

  @Autowired
  private UserRepository customerRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    logger.debug("username={}", username);

    User customer = customerRepository.findOneByName(username)
        .orElseThrow(() -> new UsernameNotFoundException("User name: " + username));

    boolean enabled = true;
    boolean accountNonExpired = true;
    boolean credentialsNonExpired = true;
    boolean accountNonLocked = true;

    return new org.springframework.security.core.userdetails.User(
        customer.getUuid(),
        customer.getPassword(),
        enabled,
        accountNonExpired,
        credentialsNonExpired,
        accountNonLocked,
        customer.getAuthorities());
  }
}
