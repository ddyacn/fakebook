package smpl.oauth2.service;

import java.util.List;
import java.util.Set;
import smpl.oauth2.model.User;

public interface UserService {

  List<User> findAll();

  User findByUuid(String uuid);

  User create(String name, String password, Set<String> authorities);

  void grantAuthorities(String uuid, Set<String> authorities);

  void revokeAuthority(String uuid, String authority);
}
