package git.dimitrikvirik.anychat.model.param;

import git.dimitrikvirik.anychat.model.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistration extends UserDTO {
    private String password;
    private String firstname;
    private String lastname;

}
