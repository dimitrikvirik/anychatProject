package git.dimitrikvirik.anychat.controller;


import git.dimitrikvirik.anychat.model.param.UserRegistration;
import git.dimitrikvirik.anychat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Value("${user.role}")
    private String role;

    @Autowired
    UserService userService;


    @PostMapping("/create")
    public String createuser(@RequestBody UserRegistration userRegistration) throws Exception {

     userService.create(userRegistration);
        System.out.println(role);
        return  "success";
    }
}
