package git.dimitrikvirik.ws.api.apigateway.exception;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import git.dimitrikvirik.ws.api.apigateway.exception.notFound.CustomException;
import git.dimitrikvirik.ws.api.apigateway.exception.notFound.MyNotFoundException;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.http.codec.HttpMessageWriter;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.Assert;
import org.springframework.web.reactive.function.BodyInserters;

import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.result.view.ViewResolver;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonExceptionHandler implements ErrorWebExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(JsonExceptionHandler.class);

    /**
     * MessageReader
     */
    private List<HttpMessageReader<?>> messageReaders = Collections.emptyList();

    /**
     * MessageWriter
     */
    private List<HttpMessageWriter<?>> messageWriters = Collections.emptyList();

    /**
     * ViewResolvers
     */
    private List<ViewResolver> viewResolvers = Collections.emptyList();

    /**
     * Store information after processing exceptions
     */
    private final ThreadLocal<Map<String, Object>> exceptionHandlerResult = new ThreadLocal<>();

    /**
     * Reference AbstractErrorWebExceptionHandler
     */
    public void setMessageReaders(List<HttpMessageReader<?>> messageReaders) {
        Assert.notNull(messageReaders, "'messageReaders' must not be null");
        this.messageReaders = messageReaders;
    }

    /**
     * Reference AbstractErrorWebExceptionHandler
     */
    public void setViewResolvers(List<ViewResolver> viewResolvers) {
        this.viewResolvers = viewResolvers;
    }

    /**
     * Reference AbstractErrorWebExceptionHandler
     */
    public void setMessageWriters(List<HttpMessageWriter<?>> messageWriters) {
        Assert.notNull(messageWriters, "'messageWriters' must not be null");
        this.messageWriters = messageWriters;
    }

    @NotNull
    @Override
    public Mono<Void> handle(@NotNull ServerWebExchange exchange, @NotNull Throwable ex) {
        HttpStatus httpStatus;
        String body;
        int code;
        if (ex instanceof NotFoundException) {
            httpStatus = HttpStatus.NOT_FOUND;
            body = "Service Not Found";
            code = 503;
        } else if (ex instanceof ResponseStatusException) {
            ResponseStatusException responseStatusException = (ResponseStatusException) ex;
            httpStatus = responseStatusException.getStatus();
            body = responseStatusException.getMessage();
            code = 500;
        }
        else if(ex instanceof CustomException){
            httpStatus = ((CustomException) ex).getHttpStatus();
            body = ex.getMessage();
            code = ((CustomException) ex).getCode();
        }
        else {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            body = "Internal Server Error";
            code = 500;
        }
        Map<String, Object> result = new HashMap<>(2, 1);
        result.put("httpStatus", httpStatus);

        assert ex instanceof MyNotFoundException;
        ExceptionBody exceptionBody = new ExceptionBody(code, ex.getClass().getSimpleName(), body, httpStatus);
        ObjectMapper objectMapper = new ObjectMapper();


        result.put("body", objectMapper.convertValue(exceptionBody, JsonNode.class));
        ServerHttpRequest request = exchange.getRequest();
        log.error("[Global Exception Handling] exception request path: {}, record exception information: {}", request.getPath(), ex.getMessage());
        if (exchange.getResponse().isCommitted()) {
            return Mono.error(ex);
        }
        exceptionHandlerResult.set(result);
        ServerRequest newRequest = ServerRequest.create(exchange, this.messageReaders);
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse).route(newRequest)
                .switchIfEmpty(Mono.error(ex))
                .flatMap((handler) -> handler.handle(newRequest))
                .flatMap((response) -> write(exchange, response));

    }

    /**
     * Refer to DefaultErrorWebExceptionHandler
     */
    @NotNull
    protected Mono<ServerResponse> renderErrorResponse(ServerRequest request) {
        Map<String, Object> result = exceptionHandlerResult.get();
        return ServerResponse.status((HttpStatus) result.get("httpStatus"))
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(result.get("body")));
    }

    /**
     * Reference AbstractErrorWebExceptionHandler
     */
    private Mono<? extends Void> write(ServerWebExchange exchange,
                                       ServerResponse response) {
        exchange.getResponse().getHeaders()
                .setContentType(response.headers().getContentType());
        return response.writeTo(exchange, new ResponseContext());
    }

    /**
     * Reference AbstractErrorWebExceptionHandler
     */
    private class ResponseContext implements ServerResponse.Context {

        @NotNull
        @Override
        public List<HttpMessageWriter<?>> messageWriters() {
            return JsonExceptionHandler.this.messageWriters;
        }

        @NotNull
        @Override
        public List<ViewResolver> viewResolvers() {
            return JsonExceptionHandler.this.viewResolvers;
        }
    }
}
