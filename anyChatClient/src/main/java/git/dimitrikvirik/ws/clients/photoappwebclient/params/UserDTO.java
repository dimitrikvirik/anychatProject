package git.dimitrikvirik.ws.clients.photoappwebclient.params;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String name;
    private String country;
    private String city;
    private String phone;
    private String email;
}
