package smpl.oauth2.repository;

import java.util.Optional;
import smpl.oauth2.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository
    extends JpaRepository<User, Long> {

  Optional<User> findOneByName(String name);

  Optional<User> findOneByUuid(String uuid);
}
