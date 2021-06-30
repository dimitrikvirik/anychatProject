package git.dimitrikvirik.anychat.exception;

import javassist.NotFoundException;

public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException(String msg) {
        super(msg);
    }
}
