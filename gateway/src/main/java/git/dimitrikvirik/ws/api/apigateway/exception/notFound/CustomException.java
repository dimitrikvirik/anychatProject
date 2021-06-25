package git.dimitrikvirik.ws.api.apigateway.exception.notFound;

import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
public class CustomException extends Exception {
    private final int code;
    private final HttpStatus httpStatus;
    public CustomException(String message, int code, HttpStatus httpStatus) {
        super(message);
        this.code = code;
        this.httpStatus =  httpStatus;
    }
    public CustomException(String message, HttpStatus httpStatus){
        super(message);
        this.code = httpStatus.value();
        this.httpStatus = httpStatus;
    }
    public CustomException(String message, int code){
        super(message);
        this.code = code;
        this.httpStatus = HttpStatus.valueOf(code);
    }


}
