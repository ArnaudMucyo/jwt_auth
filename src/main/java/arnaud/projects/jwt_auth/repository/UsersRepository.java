package arnaud.projects.jwt_auth.repository;

import arnaud.projects.jwt_auth.Models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {

    @Query(value = "select * from users where username = :username",nativeQuery = true)
    Users checkUsername(@Param("username") String username);

    @Query(value = "select password from users where username = :username",nativeQuery = true)
    String checkPassword(@Param("username") String username);
}
