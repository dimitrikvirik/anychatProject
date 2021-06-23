package git.dimitrikvirik.anychat.service;

import git.dimitrikvirik.anychat.model.param.UserRegistration;
import lombok.AllArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UsersResource;

import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@AllArgsConstructor
public class UserService {

    private final   Keycloak keycloak;

    public void create(UserRegistration userRegistration){
        UsersResource usersResource = keycloak.realm("appsdeveloperblog").users();

        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setFirstName(userRegistration.getFirstname());
        userRepresentation.setLastName(userRegistration.getLastname());
        userRepresentation.setEmail(userRegistration.getEmail());
        userRepresentation.setEnabled(true);

        Map<String, List<String>> attributes = new HashMap<>();
        attributes.put("country", Collections.singletonList(userRegistration.getCountry()));
        attributes.put("city", Collections.singletonList(userRegistration.getCity()));
        attributes.put("phone", Collections.singletonList(userRegistration.getPhone()));
        attributes.put("expire-date",
                Collections.singletonList(
                        LocalDateTime.now().plusMonths(1).format(
                                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                        )));
        userRepresentation.setAttributes(attributes);

        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        credentialRepresentation.setValue(userRegistration.getPassword());
        credentialRepresentation.setTemporary(false);
        userRepresentation.setCredentials(Collections.singletonList(credentialRepresentation));

        usersResource.create(userRepresentation);
    }

}
