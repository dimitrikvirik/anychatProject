package git.dimitrikvirik.ws.api.apigateway;


import git.dimitrikvirik.ws.api.apigateway.exception.notFound.MyNotFoundException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Arrays;

@EnableDiscoveryClient
@SpringBootApplication
@RestController
public class ApiGatewayApplication {



    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }


}
