package git.dimitrikvirik.ws.api.apigateway;


import git.dimitrikvirik.ws.api.apigateway.exception.notFound.MyNotFoundException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@EnableDiscoveryClient
@SpringBootApplication
@RestController
public class ApiGatewayApplication {



    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }
    @GetMapping(value = "/token")
    public String getHome() throws  MyNotFoundException {
    try {
        return (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }catch (NullPointerException e){
        throw  new MyNotFoundException("Principal not found!", 404);
    }
  /*     Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = Unirest.post("http://localhost:8080/auth/realms/appsdeveloperblog/protocol/openid-connect/token")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .field("username", "dima@mail.ru")
                .field("password", "123")
                .field("grant_type", "password")
                .field("client_id", "anychat-user")
                .field("client_secret", "6b69691c-e35a-441c-9809-a64e9ece9dea")
                .field("scope", "openid profile roles")
                .asString();
        return  response.getBody();*/

    }
    @PostMapping("/api/auth/login")
    public String login(){
        return "Login Page....";
    }


}
