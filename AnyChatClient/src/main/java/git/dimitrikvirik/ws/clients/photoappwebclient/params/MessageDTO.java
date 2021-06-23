package git.dimitrikvirik.ws.clients.photoappwebclient.params;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MessageDTO {
    public String author;
    public String text;
    public Date createDate;


}
