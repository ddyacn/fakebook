package smpl.oauth2.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(builderMethodName = "of")
public class ShortClientData {

  private String clientId;
  private String resourceIds;
  private String scope;
  private String authorizedGrantTypes;
  private String webServerRedirectUri;
  private String authorities;
  private String additionalInformation;
  private String autoApprove;
  private Integer accessTokenValidity;
  private Integer refreshTokenValidity;
}
