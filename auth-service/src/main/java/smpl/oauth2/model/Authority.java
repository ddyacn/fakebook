package smpl.oauth2.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;

@Data
@NoArgsConstructor
@Entity(name = "authority")
public class Authority implements GrantedAuthority {

  @JsonIgnore
  @Id
  @GeneratedValue
  private long id;

  @NotEmpty
  private String authority;

  public Authority(String authority) {
    this.authority = authority;
  }

  @Override
  public String getAuthority() {
    return authority;
  }
}
