package git.dimitrikvirik.anychat.controller;

import git.dimitrikvirik.anychat.facade.MessageFacade;
import git.dimitrikvirik.anychat.model.dto.MessageDTO;
import git.dimitrikvirik.anychat.service.MessageService;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.springsecurity.account.SimpleKeycloakAccount;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/message")
public class MessageRestController {

    @Autowired
    MessageService messageService;
    @Autowired
    MessageFacade messageFacade;
    @Autowired
    Keycloak keycloak;

    @PreAuthorize("hasRole('user')")
    @PostMapping("/send")
    public String send(@RequestBody String text, Principal principal){
        messageService.send(text, principal.getName());
        return "success";
    }
    @PreAuthorize("hasRole('admin')")
    @GetMapping("/all")
    public List<MessageDTO> getAllMessage() throws Exception {

        var msgs =  messageFacade.getAll();
        System.out.println(msgs);
        return  msgs;
    }
    @PreAuthorize("hasRole('admin')")
    @DeleteMapping("/del/{id}")
    public String delete(@PathVariable long id){
        messageService.delete(id);
        return "success";
    }


}
