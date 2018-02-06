package smpl.oauth2.mapper;

import static java.util.stream.Collectors.toSet;

import smpl.oauth2.dto.ShortUserData;
import smpl.oauth2.model.Authority;
import smpl.oauth2.model.User;
import org.springframework.stereotype.Component;

@Component
public class ShortUserDataMapper implements Mapper<User, ShortUserData> {

  @Override
  public ShortUserData map(User source) {

    if (source == null) {
      return null;
    }

    return ShortUserData.of()
        .age(source.getAge())
        .uuid(source.getUuid())
        .name(source.getName())
        .authorities(source.getAuthorities().stream()
            .map(Authority::getAuthority)
            .collect(toSet()))
        .build();
  }
}
