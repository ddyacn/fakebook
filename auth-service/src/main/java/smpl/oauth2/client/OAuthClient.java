package smpl.oauth2.client;

import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;

public interface OAuthClient {

  DefaultOAuth2AccessToken getAccessToken(
      OAuthGetAccessTokenRequest accessTokenRequest) throws UnirestException;
}
