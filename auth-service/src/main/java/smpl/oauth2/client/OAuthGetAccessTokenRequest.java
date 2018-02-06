package smpl.oauth2.client;

import javax.servlet.http.HttpServletRequest;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(builderMethodName = "of")
public class OAuthGetAccessTokenRequest {

  HttpServletRequest request;

  String clientId;
  String clientPassword;
  String username;
  String password;
  String grantType;
  String scope;
}
