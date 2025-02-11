package book.example.book.repository;

import book.example.book.enity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    @Query("SELECT r.name, f.name,f.url FROM User u " +
            "JOIN u.roles r " +
            "JOIN r.roleFunctions rf " +
            "JOIN rf.function f " +
//            "JOIN f.functionPermissions fp " +
//            "JOIN fp.permission p " +
            "WHERE u.username = :username")
    List<Object[]> findRolesAndFunctionsByUsername(String username);
}
