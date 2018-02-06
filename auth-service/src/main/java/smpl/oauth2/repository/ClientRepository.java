package smpl.oauth2.repository;

import smpl.oauth2.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {

  Client findByClientId(String clientId);

  void deleteByClientId(String clientId);
}
