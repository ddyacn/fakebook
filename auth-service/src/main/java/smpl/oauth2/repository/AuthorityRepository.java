package smpl.oauth2.repository;

import smpl.oauth2.model.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository
    extends JpaRepository<Authority, Long> {

}
