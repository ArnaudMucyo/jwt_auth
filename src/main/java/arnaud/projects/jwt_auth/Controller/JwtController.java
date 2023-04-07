package arnaud.projects.jwt_auth.Controller;

import arnaud.projects.jwt_auth.Models.Users;
import arnaud.projects.jwt_auth.service.LoginService;
import arnaud.projects.jwt_auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@RequestMapping(value = "/jwt")
public class JwtController {

    @Autowired
    private UserService userService;

    @Autowired
    private LoginService loginService;

    @PostMapping(value = "/register/user")
    public Object userRegistration(@RequestBody Users users) throws SQLException {
        return userService.registerUser(users);
    }


    @PostMapping("/login")
    public Object loginUser(@RequestBody Users user) throws Exception {
        return loginService.userLogin(user);
    }
}
