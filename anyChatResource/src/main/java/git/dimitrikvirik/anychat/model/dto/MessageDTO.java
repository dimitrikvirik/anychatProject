package git.dimitrikvirik.anychat.model.dto;

import git.dimitrikvirik.anychat.model.entity.Message;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MessageDTO {
    public String author;
    public String text;
    public Date createDate;

    public static MessageDTO toMessageDTO(Message message){
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setText(message.getText());
        messageDTO.setCreateDate(message.getCreateDate());
        return messageDTO;
    }
}
