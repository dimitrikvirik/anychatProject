package git.dimitrikvirik.ws.api.apigateway.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class ExceptionBody {
    private final int code;
    private final String exception_name;
    private final String message;
    private final HttpStatus http_status;
}
