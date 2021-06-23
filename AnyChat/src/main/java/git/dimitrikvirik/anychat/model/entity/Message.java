package git.dimitrikvirik.anychat.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;


@Table(name = "MESSAGE", schema = "chat")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "text")
    private String text;
    @Column(name  = "create_date")
    private final Date createDate = new Date();
    @Column(name = "edit_date")
    private Date editDate = new Date();
    @Column(name  = "user_kid")
    private String userKID;
    @Column(name = "test")
    private int test;
}