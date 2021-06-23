package git.dimitrikvirik.ws.clients.photoappwebclient.params;

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
