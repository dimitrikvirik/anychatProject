package git.dimitrikvirik.anychat.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import git.dimitrikvirik.anychat.exception.UserNotFoundException;
import git.dimitrikvirik.anychat.model.dto.UserEmailDTO;
import io.swagger.annotations.Api;
import org.apache.catalina.User;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/admin")
@RestController
@Api("Admin")
public class AdminRestController {
    @Autowired
    Keycloak keycloak;
    @PreAuthorize("hasRole('admin')")
    @GetMapping("/user-roles/{username}")
    public List<String> getUserRoles(@PathVariable String username) throws UserNotFoundException {
        UserResource userResource = getUserByUsername(username);
         return userResource.roles().realmLevel().listEffective().stream().map(
                    (RoleRepresentation::getName)
            ).collect(Collectors.toList());

    }
    @PreAuthorize("hasRole('admin')")
    @GetMapping("/set-role/{username}/{role}")
    public void setUserRole(@PathVariable String username, @PathVariable String role) throws UserNotFoundException {
        UserResource userResource = getUserByUsername(username);
            RoleRepresentation existedRole = userResource.roles().realmLevel().listAvailable().stream()
                    .filter((roleRepresentation -> roleRepresentation.getName().equals("admin")))
                    .collect(Collectors.toList()).get(0);
            if(existedRole != null){
                userResource.roles().realmLevel().add(Collections.singletonList(existedRole));
            }
    }

    public UserResource getUserByUsername(String username) throws UserNotFoundException {
        UsersResource usersResource =  keycloak.realm("anychat").users();
            List<UserRepresentation> search = usersResource.search(username);
            if(search.isEmpty()) throw new UserNotFoundException("User not found");
        UserRepresentation user =  search.get(0);
        return   usersResource.get(
                user.getId());
    }
}
