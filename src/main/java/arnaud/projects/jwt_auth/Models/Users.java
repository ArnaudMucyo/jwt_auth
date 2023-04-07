package arnaud.projects.jwt_auth.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 200)
    private String firstname;
    @Column(length = 200)
    private String lastname;
    @Column(length = 20)
    private String username;

    @Column(length = 200)
    private String password;
}
