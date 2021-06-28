package git.dimitrikvirik.anychat.security;

import com.google.common.base.Predicates;
import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.*;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(buildSecurityScheme()).securityContexts(buildSecurityContext());
    }
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Swagger API")
                .description("test")
                .termsOfServiceUrl("")
                .contact(new Contact("wd", "", ""))
                .version("2.0")
                .build();
    }

    @Bean
    public SecurityConfiguration securityConfiguration() {

        Map<String, Object> additionalQueryStringParams=new HashMap<>();
        additionalQueryStringParams.put("nonce","123456");

        return SecurityConfigurationBuilder.builder()
                .clientId("superclient")
                .clientSecret("135f6f8f-365d-44bf-b7ed-4f753ff9b2c8")
                .realm("anychat").appName("swagger-ui")
                .useBasicAuthenticationWithAccessCodeGrant(true)
                .additionalQueryStringParams(additionalQueryStringParams)
                .build();
    }

    private List<SecurityContext> buildSecurityContext() {
        List<SecurityReference> securityReferences = new ArrayList<>();

        securityReferences.add(SecurityReference.builder().reference("oauth2").scopes(scopes().toArray(new AuthorizationScope[]{})).reference("Authorization").build());
        SecurityContext context = SecurityContext.builder().forPaths(Predicates.alwaysTrue()).securityReferences(securityReferences).build();

        List<SecurityContext> ret = new ArrayList<>();
        ret.add(context);
        return ret;
    }
    private List<? extends SecurityScheme> buildSecurityScheme() {
        List<SecurityScheme> lst = new ArrayList<>();
       lst.add(new ApiKey("Authorization", "Authorization", "header"));
         LoginEndpoint login = new LoginEndpointBuilder().url("http://localhost:8080/auth/realms/anychat/protocol/openid-connect/auth").build();

        List<GrantType> gTypes = new ArrayList<>();
        gTypes.add(new ImplicitGrant(login, "access_token"));

        lst.add(new OAuth("oauth2", scopes(), gTypes));
        return lst;
    }

    private List<AuthorizationScope> scopes() {
        List<AuthorizationScope> scopes = new ArrayList<>();
        for (String scopeItem : new String[]{"openid=openid", "profile=profile"}) {
            String scope[] = scopeItem.split("=");
            if (scope.length == 2) {
                scopes.add(new AuthorizationScopeBuilder().scope(scope[0]).description(scope[1]).build());
            } else {

                System.out.println("some ERROR");
            }
        }

        return scopes;
    }

}