package smpl.oauth2.service;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import smpl.oauth2.model.Authority;
import smpl.oauth2.model.User;
import smpl.oauth2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  @Override
  public List<User> findAll() {
    return userRepository.findAll();
  }

  @Override
  public User findByUuid(String uuid) {
    return userRepository.findOneByUuid(uuid)
        .orElseThrow(UserNotFoundException::new);
  }

  @Override
  public User create(String name, String password, Set<String> authorities) {

    User user = User.of()
        .name(name)
        .password(new BCryptPasswordEncoder().encode(password))
        .authorities(authorities.stream()
            .map(Authority::new)
            .collect(Collectors.toSet()))
        .uuid(UUID.randomUUID().toString())
        .age(new Random().nextInt(20) + 20)
        .build();

    userRepository.save(user);

    return user;
  }

  @Override
  public void grantAuthorities(String uuid, Set<String> authorities) {

    User user = userRepository.findOneByUuid(uuid)
        .orElseThrow(UserNotFoundException::new);

    Set<String> userAuthorities = user.getAuthorities()
        .stream()
        .map(Authority::getAuthority)
        .collect(Collectors.toSet());

    authorities.removeIf(userAuthorities::contains);

    user.getAuthorities().addAll(
        authorities.stream()
            .map(Authority::new)
            .collect(Collectors.toSet()));

    userRepository.save(user);
  }

  @Override
  public void revokeAuthority(String uuid, String authority) {

    User user = userRepository.findOneByUuid(uuid)
        .orElseThrow(UserNotFoundException::new);

    user.getAuthorities().removeIf(customerAuthority ->
        customerAuthority.getAuthority().equals(authority));

    userRepository.save(user);
  }

  @Autowired
  public void setUserRepository(UserRepository repository) {
    this.userRepository = repository;
  }

  private UserRepository userRepository;
}
