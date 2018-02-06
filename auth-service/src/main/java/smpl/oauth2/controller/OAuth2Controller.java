package smpl.oauth2.controller;

import com.mashape.unirest.http.exceptions.UnirestException;
import io.swagger.annotations.ApiOperation;
import javax.servlet.http.HttpServletRequest;
import smpl.oauth2.client.OAuthClient;
import smpl.oauth2.client.OAuthGetAccessTokenRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OAuth2Controller {

  @ApiOperation(tags = "OAuth2 Helper", value = "Return oauth2 access token")
  @GetMapping("/oauth2-token")
  public ResponseEntity<OAuth2AccessToken> getAccessToken(
      HttpServletRequest request,
      @RequestParam String clientId,
      @RequestParam String clientPassword,
      @RequestParam String username,
      @RequestParam String password,
      @RequestParam GrantType grantType,
      @RequestParam(defaultValue = "default") String scope)
      throws UnirestException {

    OAuthGetAccessTokenRequest accessTokenRequest =
        OAuthGetAccessTokenRequest.of()
            .clientId(clientId)
            .clientPassword(clientPassword)
            .username(username)
            .password(password)
            .grantType(grantType.getKey())
            .scope(scope)
            .request(request)
            .build();

    return ResponseEntity.ok(oauthClient.getAccessToken(accessTokenRequest));
  }

  private enum GrantType {

    PASSWORD("password");

    private String key;

    GrantType(String key) {
      this.key = key;
    }

    public String getKey() {
      return key;
    }
  }

  @Autowired
  public void setOAuthClient(OAuthClient client) {
    this.oauthClient = client;
  }

  private OAuthClient oauthClient;
}
