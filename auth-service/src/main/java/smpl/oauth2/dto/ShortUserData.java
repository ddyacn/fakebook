package smpl.oauth2.dto;

import java.util.Set;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(builderMethodName = "of")
public class ShortUserData {

  private int age;
  private String uuid;
  private String name;
  private Set<String> authorities;
}
