package git.dimitrikvirik.anychat.controller;

import git.dimitrikvirik.anychat.exception.UserNotFoundException;
import org.apache.tomcat.util.http.parser.Authorization;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.keycloak.AuthorizationContext;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.AccessToken;
import org.keycloak.representations.idm.UserRepresentation;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.util.Assert;
import springfox.documentation.spi.service.contexts.SecurityContextBuilder;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AdminRestControllerTest {
    @Autowired
    @Mock
    Keycloak keycloak;
    @Autowired
    AdminRestController adminRestController;


    private String existUsername;
    private String unExistUsername;
    private String existRole;
    private String unExistRole;


    @BeforeEach
    void setUp() {
        existUsername = "maka";
        unExistUsername = "bbbb";

        existRole = "admin";
        unExistRole = "bbb";


        UserRepresentation userRepresentation = keycloak.realm("anychat").users().get("83f418b6-1e20-4985-8e0d-d6790bda115a").toRepresentation();
        userRepresentation.getOrigin();

        SecurityContextHolder.setContext(
                new SecurityContextImpl()
        );

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getUserRoles() throws UserNotFoundException {
        Assert.notEmpty(adminRestController.getUserRoles(existUsername), "User role list must not be empty!");
        assertThrows(UserNotFoundException.class, ()-> adminRestController.getUserRoles(unExistUsername));
    }

    @Test
    void setUserRole() {
    }

    @Test
    void getUserByUsername() throws UserNotFoundException {
        Assert.notNull(adminRestController.getUserByUsername(existUsername), String.format("User %s must not  give null", existUsername));
        assertThrows(UserNotFoundException.class, ()-> adminRestController.getUserByUsername(unExistUsername));

    }
}