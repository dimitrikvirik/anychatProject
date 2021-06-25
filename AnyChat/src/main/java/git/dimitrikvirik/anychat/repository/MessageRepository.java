package git.dimitrikvirik.anychat.repository;

import git.dimitrikvirik.anychat.model.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {


}