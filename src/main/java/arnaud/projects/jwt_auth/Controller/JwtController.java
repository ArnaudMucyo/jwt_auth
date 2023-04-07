package arnaud.projects.jwt_auth.Controller;

import arnaud.projects.jwt_auth.Models.Response;
import arnaud.projects.jwt_auth.Models.Users;
import arnaud.projects.jwt_auth.service.JwtService;
import arnaud.projects.jwt_auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.sql.SQLException;

@RestController
@RequestMapping(value = "/jwt")
public class JwtController {

    @Autowired
    private UserService userService;


    @PostMapping(value = "/register/user")
    public Object userRegistration(@RequestBody Users users) throws SQLException {
        return userService.registerUser(users);
    }
}
