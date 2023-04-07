package arnaud.projects.jwt_auth.service;
import arnaud.projects.jwt_auth.Models.CatchResponse;
import arnaud.projects.jwt_auth.Models.LoginResponse;
import arnaud.projects.jwt_auth.Models.Users;
import arnaud.projects.jwt_auth.repository.UsersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Service
public class LoginService {

    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;
    private static final Logger Log = LoggerFactory.getLogger(LoginService.class);

    public boolean usernameExists(String username) {
        Users user = usersRepository.checkUsername(username);
        return user != null;
    }

    public boolean verifyPassword(String username, String password) {
        String storedPassword = usersRepository.checkPassword(username);
        return passwordEncoder.matches(password, storedPassword);
    }

    public Object userLogin(Users user) throws Exception {

        LoginResponse loginResponse = new LoginResponse();

        try {

            if (usernameExists(user.getUsername())) {
                if (verifyPassword(user.getUsername(), user.getPassword())) {
                    String token = jwtUtil.generateToken(user.getUsername());
                    loginResponse.setStatus("SUCCESS");
                    loginResponse.setToken(token);
                    loginResponse.setMessage("Successful login");
                    LocalDateTime expirationTime = jwtUtil.extractExpiration(token).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    loginResponse.setExpiration(expirationTime.format(formatter));
                    Log.info("User " + user.getUsername() + " successfully logged in, token expires at : " + loginResponse.getExpiration());
                    return loginResponse;
                }
                else {
                    loginResponse.setStatus("FAILED");
                    loginResponse.setMessage("Incorrect password. Please enter the correct password");
                    Log.error("Password entered is incorrect");
                    return loginResponse;
                }
            } else  {
                loginResponse.setStatus("FAILED");
                loginResponse.setMessage("Username provided does not exist. Please enter a valid username");
                Log.warn("Username " + user.getUsername() + " does not exist");
                return loginResponse;
            }
        } catch (Exception e) {
            Log.error("Error occurred : " + e.getMessage());
            CatchResponse catchResponse = new CatchResponse();
            catchResponse.setOperation("LOGIN");
            catchResponse.setMessage("Error occurred : " + e.getMessage());
            return catchResponse;
        }

    }
}



