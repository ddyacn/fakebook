package smpl.oauth2.mapper;

import smpl.oauth2.dto.ShortClientData;
import smpl.oauth2.model.Client;
import org.springframework.stereotype.Component;

@Component
public class ShortClientDataMapper implements Mapper<Client, ShortClientData> {

  @Override
  public ShortClientData map(Client source) {

    if (source == null) {
      return null;
    }

    return ShortClientData.of()
        .clientId(source.getClientId())
        .resourceIds(source.getResourceIds())
        .scope(source.getScope())
        .authorizedGrantTypes(source.getAuthorizedGrantTypes())
        .webServerRedirectUri(source.getWebServerRedirectUri())
        .authorities(source.getAuthorities())
        .additionalInformation(source.getAdditionalInformation())
        .autoApprove(source.getAutoApprove())
        .accessTokenValidity(source.getAccessTokenValidity())
        .refreshTokenValidity(source.getRefreshTokenValidity())
        .build();
  }
}
