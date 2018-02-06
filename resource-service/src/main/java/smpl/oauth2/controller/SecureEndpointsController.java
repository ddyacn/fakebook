package smpl.oauth2.controller;

import io.swagger.annotations.ApiOperation;
import java.security.Principal;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecureEndpointsController {

  @ApiOperation(tags = TAG_ENDPOINTS, value = "Return an authenticated user")
  @GetMapping("/current-user")
  public Principal findCurrentUser(Principal user) {
    return user;
  }

  @ApiOperation(tags = TAG_ENDPOINTS, value = "Resource requires role")
  @GetMapping("/required/user-roles/{role}")
  @PreAuthorize("#oauth2.isUser() && hasRole(#role)")
  public Map<String, Object> accessWithRole(@PathVariable String role) {
    return iso8601CurrentDateTime();
  }

  @ApiOperation(tags = TAG_ENDPOINTS, value = "Resource requires authority")
  @GetMapping("/required/user-authorities/{authority}")
  @PreAuthorize("#oauth2.isUser() && hasAuthority(#authority)")
  public Map<String, Object> accessWithAuthority(@PathVariable String authority) {
    return iso8601CurrentDateTime();
  }

  @ApiOperation(tags = TAG_ENDPOINTS, value = "Resource requires authority")
  @GetMapping("/required/user-roles/{role}/user-authorities/{authority}")
  @PreAuthorize("#oauth2.isUser() && hasRole(#role) && hasAuthority(#authority)")
  public Map<String, Object> accessWithRoleAndAuthority(
      @PathVariable String role, @PathVariable String authority) {
    return iso8601CurrentDateTime();
  }

  @ApiOperation(tags = TAG_ENDPOINTS, value = "Resource requires client role and user role")
  @GetMapping("/required/user-roles/{userRole}/client-roles/{clientRole}")
  @PreAuthorize("#oauth2.clientHasRole(#clientRole) && hasRole(#userRole)")
  public Map<String, Object> accessWithUserAndClientRoles(
      @PathVariable String userRole, @PathVariable String clientRole) {
    return iso8601CurrentDateTime();
  }

  @ApiOperation(tags = TAG_ENDPOINTS, value = "Resource requires client role and user role")
  @GetMapping("/required/user-authorities/{userAuthority}/client-authorities/{clientAuthority}")
  @PreAuthorize("#oauth2.clientHasRole(#clientAuthority) && hasRole(#userAuthority)")
  public Map<String, Object> accessWithUserAndClientAuthorities(
      @PathVariable String userAuthority, @PathVariable String clientAuthority) {
    return iso8601CurrentDateTime();
  }

  private Map<String, Object> iso8601CurrentDateTime() {

    Map<String, Object> map = new HashMap<>();
    map.put("time", ZonedDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));

    return map;
  }

  private static final String TAG_ENDPOINTS = "Secure Endpoints";
}
