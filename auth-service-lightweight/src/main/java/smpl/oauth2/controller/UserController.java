package smpl.oauth2.controller;

import java.security.Principal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

  @GetMapping("/me")
  public Principal me(Principal principal) {
    return principal;
  }
}
