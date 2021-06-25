package git.dimitrikvirik.ws.api.apigateway.exception.notFound;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter

public class MyNotFoundException extends  CustomException {

    public MyNotFoundException(String message, int code) {
        super(message, code, HttpStatus.NOT_FOUND);
    }




}
