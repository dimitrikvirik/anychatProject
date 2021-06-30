package git.dimitrikvirik.anychat.exception;

import javassist.NotFoundException;

public class RoleNotFoundException extends NotFoundException {
    public RoleNotFoundException(String msg) {
        super(msg);
    }
}
