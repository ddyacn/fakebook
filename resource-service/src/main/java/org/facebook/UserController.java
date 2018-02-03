package org.facebook;

import java.security.Principal;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

  @GetMapping("/user")
  @PreAuthorize("#oauth2.isUser() && hasAnyRole('ADMIN', 'USER')")
  public Principal user(Principal principal) {
    return principal;
  }

  @GetMapping("/admin")
  @PreAuthorize("#oauth2.isUser() && hasRole('ADMIN')")
  public Principal admin(Principal principal) {
    return principal;
  }

  @GetMapping("/client")
  @PreAuthorize("hasRole('CLIENT')")
  public Principal client(Principal principal) {
    return principal;
  }
}
