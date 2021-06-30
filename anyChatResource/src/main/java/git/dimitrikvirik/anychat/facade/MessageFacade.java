package git.dimitrikvirik.anychat.facade;

import com.fasterxml.jackson.databind.ObjectMapper;
import git.dimitrikvirik.anychat.model.dto.MessageDTO;
import git.dimitrikvirik.anychat.model.entity.Message;
import git.dimitrikvirik.anychat.service.MessageService;
import git.dimitrikvirik.anychat.service.UserService;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MessageFacade {
    @Autowired
    MessageService messageService;
    @Autowired
    Keycloak keycloak;
    @Autowired
    ObjectMapper objectMapper;

    public List<MessageDTO> getAll() throws Exception {
      List<Message> messageList = messageService.getAll();
      List<MessageDTO> messages = new ArrayList<>();
        for (Message message : messageList) {
            UsersResource usersResource = keycloak.realm("appsdeveloperblog").users();
            UserResource userResource = usersResource.get(message.getUserKID());
            UserRepresentation user = userResource.toRepresentation();

            MessageDTO messageDTO =  objectMapper.convertValue(message, MessageDTO.class);
            messageDTO.setAuthor(user.getEmail());
            messages.add(messageDTO);
        }

      return messages;
    }
}
