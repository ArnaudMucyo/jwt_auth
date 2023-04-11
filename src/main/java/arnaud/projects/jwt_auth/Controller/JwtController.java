package arnaud.projects.jwt_auth.Controller;

import arnaud.projects.jwt_auth.Models.CatchResponse;
import arnaud.projects.jwt_auth.Models.LoginResponse;
import arnaud.projects.jwt_auth.Models.RegistrationResponse;
import arnaud.projects.jwt_auth.Models.Users;
import arnaud.projects.jwt_auth.service.LoginService;
import arnaud.projects.jwt_auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Object> userRegistration(@RequestBody Users users) throws SQLException {

        Object result = userService.registerUser(users);

        if (result instanceof RegistrationResponse registrationResponse) {

            switch (registrationResponse.getStatus()) {
                case "SUCCESS" -> {
                    return new ResponseEntity<>(registrationResponse, HttpStatus.CREATED);
                }
                case "DUPLICATION" -> {
                    return new ResponseEntity<>(registrationResponse, HttpStatus.CONFLICT);
                }
                case "FAILED" -> {
                    return new ResponseEntity<>(registrationResponse, HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        }
        return new ResponseEntity<>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @PostMapping("/login")
    public ResponseEntity<Object> loginUser(@RequestBody Users user) {
        Object result =  loginService.userLogin(user);

        if (result instanceof LoginResponse loginResponse){
            if (loginResponse.getStatus().equals("SUCCESS")) {
                return new ResponseEntity<>(loginResponse, HttpStatus.OK);
            }
            else return new ResponseEntity<>(loginResponse,HttpStatus.UNAUTHORIZED);
        }
        else if (result instanceof CatchResponse catchResponse) {
            return new ResponseEntity<>(catchResponse,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        else return new ResponseEntity<>("An error occurred",HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
