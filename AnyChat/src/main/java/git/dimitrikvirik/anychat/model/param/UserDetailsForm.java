package git.dimitrikvirik.anychat.model.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDetailsForm {
    private String country;
    private String city;
    private String phone;
}
