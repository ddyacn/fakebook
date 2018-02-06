package smpl.oauth2.controller;

import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.Set;
import smpl.oauth2.dto.ShortUserData;
import smpl.oauth2.mapper.ShortUserDataMapper;
import smpl.oauth2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

  @ApiOperation(tags = TAG_USERS, value = "Return a list of users")
  @GetMapping("/users")
  public List<ShortUserData> findAll() {
    return userMapper.map(userService.findAll());
  }

  @ApiOperation(tags = TAG_USERS, value = "Return user")
  @GetMapping("/users/{uuid}")
  public ShortUserData find(@PathVariable String uuid) {
    return userMapper.map(userService.findByUuid(uuid));
  }

  @ApiOperation(tags = TAG_USERS, value = "Create a new user")
  @PostMapping("/users")
  public ShortUserData create(
      @RequestParam String name,
      @RequestParam String password,
      @RequestParam Set<String> authorities) {

    return userMapper.map(userService.create(name, password, authorities));
  }

  @ApiOperation(tags = TAG_USERS, value = "Grant authorities to a user")
  @PutMapping("/users/{uuid}/authorities")
  public void grantAuthority(
      @PathVariable String uuid,
      @RequestParam Set<String> authorities) {

    userService.grantAuthorities(uuid, authorities);
  }

  @ApiOperation(tags = TAG_USERS, value = "Revoke an authority from a user")
  @DeleteMapping("/users/{uuid}/authorities/{authority}")
  public void revokeAuthority(
      @PathVariable String uuid,
      @PathVariable String authority) {

    userService.revokeAuthority(uuid, authority);
  }

  @Autowired
  public void setUserService(UserService service) {
    this.userService = service;
  }

  @Autowired
  public void setUserMapper(ShortUserDataMapper mapper) {
    this.userMapper = mapper;
  }

  private UserService userService;
  private ShortUserDataMapper userMapper;

  private static final String TAG_USERS = "Authorities and Users";
}
