package smpl.oauth2.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import java.io.IOException;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.stereotype.Service;

@Service
public class LocalOAuthClient implements OAuthClient {

  @Override
  public DefaultOAuth2AccessToken getAccessToken(OAuthGetAccessTokenRequest accessTokenRequest)
      throws UnirestException {

    String oauthTokenUrl = "/oauth/token";

    String url = String.format("%s://%s:%d%s%s",
        accessTokenRequest.getRequest().getScheme(),
        accessTokenRequest.getRequest().getLocalName(),
        accessTokenRequest.getRequest().getServerPort(),
        accessTokenRequest.getRequest().getContextPath(),
        oauthTokenUrl);

    Unirest.setObjectMapper(new ObjectMapper() {
      private com.fasterxml.jackson.databind.ObjectMapper jacksonObjectMapper
          = new com.fasterxml.jackson.databind.ObjectMapper();

      public <T> T readValue(String value, Class<T> valueType) {
        try {
          return jacksonObjectMapper.readValue(value, valueType);
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
      }

      public String writeValue(Object value) {
        try {
          return jacksonObjectMapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
          throw new RuntimeException(e);
        }
      }
    });

    HttpResponse<DefaultOAuth2AccessToken> response = Unirest.post(url)
        .basicAuth(accessTokenRequest.getClientId(), accessTokenRequest.getClientPassword())
        .queryString("grant_type", accessTokenRequest.getGrantType())
        .queryString("username", accessTokenRequest.getUsername())
        .queryString("password", accessTokenRequest.getPassword())
        .queryString("scope", accessTokenRequest.getScope())
        .asObject(DefaultOAuth2AccessToken.class);

    return response.getBody();
  }
}
