package smpl.oauth2.controller;

import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.transaction.Transactional;
import smpl.oauth2.dto.ShortClientData;
import smpl.oauth2.mapper.ShortClientDataMapper;
import smpl.oauth2.model.Client;
import smpl.oauth2.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClientController {

  @ApiOperation(tags = "OAuth2 Helper", value = "Return a list of clients")
  @GetMapping("/clients")
  public List<ShortClientData> findAll() {
    return mapper.map(clientRepository.findAll());
  }

  @ApiOperation(tags = "OAuth2 Helper", value = "Return a list of clients")
  @DeleteMapping("/clients/{clientId}")
  @Transactional
  public void deleteClient(@PathVariable String clientId) {
    clientRepository.deleteByClientId(clientId);
  }

  @ApiOperation(tags = "OAuth2 Helper", value = "Create new client")
  @PostMapping("/clients")
  public ShortClientData createClient(
      @RequestParam String clientId,
      @RequestParam(required = false) String resourceIds,
      @RequestParam String clientSecret,
      @RequestParam(defaultValue = "default") String scope,
      @RequestParam(defaultValue = "password,client_credentials,refresh_token")
          String authorizedGrantTypes,
      @RequestParam(required = false) String webServerRedirectUri,
      @RequestParam(required = false) String authorities,
      @RequestParam(defaultValue = "1800") Integer accessTokenValidity,
      @RequestParam(defaultValue = "1800") Integer refreshTokenValidity,
      @RequestParam(defaultValue = "default") String autoApprove) {

    Client client = clientRepository.save(Client.of()
        .clientId(clientId)
        .resourceIds(resourceIds == null ? "" : resourceIds)
        .clientSecret(new BCryptPasswordEncoder().encode(clientSecret))
        .scope(scope)
        .authorizedGrantTypes(authorizedGrantTypes)
        .webServerRedirectUri(webServerRedirectUri == null ? "" : webServerRedirectUri)
        .authorities(authorities == null ? "" : authorities)
        .accessTokenValidity(accessTokenValidity)
        .refreshTokenValidity(refreshTokenValidity)
        .additionalInformation("{}")
        .autoApprove(autoApprove)
        .build());

    return mapper.map(client);
  }

  @Autowired
  public void setClientRepository(ClientRepository repository) {
    this.clientRepository = repository;
  }

  @Autowired
  public void setMapper(ShortClientDataMapper mapper) {
    this.mapper = mapper;
  }

  private ShortClientDataMapper mapper;
  private ClientRepository clientRepository;
}
