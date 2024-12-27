package book.example.book.repository;

import book.example.book.enity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PermissionRepository extends JpaRepository<Permission, Long> {


    @Query("SELECT DISTINCT p FROM User u " +
            "JOIN u.roles r " +
            "JOIN  r.rolePermissions rp  " +
            "JOIN rp.permission p " +
            "WHERE u.username = :username")
    List<Permission> findPermissionsByUsername(String username);

    Optional<Permission> findByName(String name);





}


