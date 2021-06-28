package git.dimitrikvirik.anychat.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import git.dimitrikvirik.anychat.model.dto.UserDTO;
import git.dimitrikvirik.anychat.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.*;
import org.keycloak.representations.idm.*;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RequestMapping("/user")
@RestController
@Api("user")
public class UserRestController {


    @Autowired
    UserService userService;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    Keycloak keycloak;

    @GetMapping
    @ApiOperation(value = "User Info", notes = "Addition")
    @PreAuthorize("hasRole('user')")
    public UserDTO getCurrentUser() throws Exception {
        JwtAuthenticationToken token = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
     return objectMapper.convertValue(token.getToken().getClaims(), UserDTO.class);
    }
    @PutMapping("/expire")
    @PreAuthorize("hasRole('admin')")
    public String expireUser(Principal principal){
    JwtAuthenticationToken token = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
     var scopes =  (String)  token.getToken().getClaims().get("scope");
        UsersResource  usersResource  = keycloak.realm("appsdeveloperblog").users();
     if(scopes.contains("expire-date-all")){
         List<UserRepresentation> userRepresentationList = usersResource.list();
            userRepresentationList.forEach((user -> {
                addUserExpireMonth(user);
                usersResource.get(user.getId()).update(user);
            }));
        }
        else{
            var resource =  usersResource.get(principal.getName());
            var userRepresentation = resource.toRepresentation();
            addUserExpireMonth(userRepresentation);
            resource.update(userRepresentation);
        }
        return "Success!";
    }

    private void addUserExpireMonth(UserRepresentation user) {
        var newAtr = user.getAttributes();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        newAtr.get("expire-date").set(0,
                LocalDateTime.parse(newAtr.get("expire-date").get(0), formatter)
                        .plusMonths(1).format(formatter));
        user.setAttributes(newAtr);
    }


}
