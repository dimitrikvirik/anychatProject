package git.dimitrikvirik.anychat.service;

import git.dimitrikvirik.anychat.model.entity.Message;
import git.dimitrikvirik.anychat.repository.MessageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
@AllArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;

    public void send(String text, String KID){
            Message message = new Message();
            message.setText(text);
            message.setUserKID(KID);
            messageRepository.save(message);
    }
    public List<Message> getAll(){
     return  messageRepository.findAll();
    }

    public void delete(long id) {
        messageRepository.deleteById(id);
    }
}


