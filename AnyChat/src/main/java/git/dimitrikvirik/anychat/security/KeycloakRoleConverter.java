package git.dimitrikvirik.anychat.security;








import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.thymeleaf.util.ListUtils;


import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class KeycloakRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {
    
    @Override
    public Collection<GrantedAuthority> convert(Jwt source) {

        Map<String, Object> realmAccess = (Map<String, Object>) source.getClaims().get("realm_access");
        if(realmAccess == null || realmAccess.isEmpty()){
            return new ArrayList<>();
        }
        List<SimpleGrantedAuthority> roles = ((List<String>) realmAccess.get("roles"))
                .stream().map(roleName -> "ROLE_" + roleName)
                .map(SimpleGrantedAuthority::new).collect(Collectors.toList());

        String scope = (String) source.getClaims().get("scope");
        String[] scopes = scope.split("\\s+");
        List<SimpleGrantedAuthority> scopeList = Arrays.stream(scopes).map(scopeName -> "SCOPE_" + scopeName)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return Stream.concat(roles.stream(), scopeList.stream())
                .collect(Collectors.toList());
    }

}