package arnaud.projects.jwt_auth.repository;

import arnaud.projects.jwt_auth.Models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {


//    @Modifying
//    @Query(value = "INSERT INTO users (username,password) values (:username,:password)")
//    void insertUser(@Param("username") String username,@Param("password") String password);
}
