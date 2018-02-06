package smpl.oauth2.config;

import static java.util.stream.Collectors.toSet;

import java.util.Random;
import java.util.UUID;
import java.util.stream.Stream;
import javax.annotation.PostConstruct;
import smpl.oauth2.model.Authority;
import smpl.oauth2.model.Client;
import smpl.oauth2.model.User;
import smpl.oauth2.repository.ClientRepository;
import smpl.oauth2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class InitUsersConfig {

  private UserRepository userRepository;

  private ClientRepository clientRepository;

  @Autowired
  public void setUserRepository(UserRepository repository) {
    this.userRepository = repository;
  }

  @Autowired
  public void setClientRepository(ClientRepository repository) {
    this.clientRepository = repository;
  }

  @PostConstruct
  public void init() {

    userRepository.save(User.of()
        .name("mark")
        .uuid(UUID.randomUUID().toString())
        .age(new Random().nextInt(80) + 20)
        .password(new BCryptPasswordEncoder().encode("u11"))
        .authorities(
            Stream.of("ROLE_ADMIN", "OP_EDIT_USER", "OP_DELETE_USER", "OP_VIEW_USER")
                .map(Authority::new)
                .collect(toSet()))
        .build());

    userRepository.save(User.of()
        .name("ben")
        .uuid(UUID.randomUUID().toString())
        .age(new Random().nextInt(80) + 20)
        .password(new BCryptPasswordEncoder().encode("u22"))
        .authorities(
            Stream.of("ROLE_USER", "OP_VIEW_USER")
                .map(Authority::new)
                .collect(toSet()))
        .build());

    clientRepository.save(Client.of()
        .clientId("idm")
        .resourceIds("")
        .clientSecret(new BCryptPasswordEncoder().encode("c11"))
        .scope("default")
        .authorizedGrantTypes("password,client_credentials")
        .webServerRedirectUri("")
        .authorities("ROLE_TRUSTED_CLIENT,OP_CLIENT_OPERATION")
        .accessTokenValidity(1800)
        .refreshTokenValidity(1800)
        .additionalInformation("{}")
        .autoApprove("default")
        .build());

    clientRepository.save(Client.of()
        .clientId("idm_client")
        .resourceIds("")
        .clientSecret(new BCryptPasswordEncoder().encode("c22"))
        .scope("default")
        .authorizedGrantTypes("refresh_token,client_credentials")
        .webServerRedirectUri("")
        .authorities("ROLE_TRUSTED_CLIENT,OP_CLIENT_OPERATION")
        .accessTokenValidity(1800)
        .refreshTokenValidity(1800)
        .additionalInformation("{}")
        .autoApprove("default")
        .build());
  }
}
