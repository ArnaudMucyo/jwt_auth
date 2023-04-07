package arnaud.projects.jwt_auth.service;

import arnaud.projects.jwt_auth.Models.RegistrationResponse;
import arnaud.projects.jwt_auth.Models.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.*;

@Service
public class UserService {

    final String user = "root";
    final String password = "Arno@7414";;
    final String url = "jdbc:mysql://localhost:3306/jwt_db";

    Connection connection = null;
    private static final Logger Log = LoggerFactory.getLogger(LoginService.class);

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Object registerUser(Users users) throws SQLException {

        RegistrationResponse registrationResponse = new RegistrationResponse();

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String checkUsername = "SELECT * FROM users WHERE username = ?";
            PreparedStatement stmt = connection.prepareStatement(checkUsername);
            stmt.setString(1, users.getUsername());
            ResultSet resultSet = stmt.executeQuery();

            try {

                if (resultSet.next()) {
                    //Response duplicationResponse = new Response();
                    registrationResponse.setStatus("DUPLICATION");
                    registrationResponse.setUsername("Username " + users.getUsername() + " exists");
                    Log.warn("Username " + users.getUsername() + " exists, please use another username");
                } else {
                    String insertUserQuery = "INSERT INTO users (firstname, lastname, username, password) VALUES (?, ?, ?, ?)";
                    PreparedStatement insertUser = connection.prepareStatement(insertUserQuery);
                    insertUser.setString(1, users.getFirstname());
                    insertUser.setString(2, users.getLastname());
                    insertUser.setString(3, users.getUsername());
                    insertUser.setString(4, passwordEncoder.encode(users.getPassword()));
                    insertUser.execute();
                    Log.info("User " + users.getUsername() + " created successfully!");
                    //Response successResponse = new Response();
                    registrationResponse.setStatus("SUCCESS");
                    registrationResponse.setUsername(users.getUsername());
                    return registrationResponse;

                }

            }
            finally {
                resultSet.close();
            }
            
        }

        catch (SQLException e) {
            e.printStackTrace();
            Log.error("Error occurred: " + e.getMessage());
            //Response errorResponse = new Response();
            registrationResponse.setStatus("FAILED");
            registrationResponse.setUsername("There was an error while registering user");
            return registrationResponse;
        }

        return registrationResponse;


    }
}
