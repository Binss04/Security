package book.example.book.repository;

import book.example.book.enity.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface RoleRepository extends CrudRepository<Role,Long> {
    Optional<Role> findByName(String name);



    @Query("SELECT u.username AS username, " +
            "       r.name AS roleName, " +
            "       p.name AS permissionName " +
            "FROM User u " +
            "LEFT JOIN u.roles r " +
            "LEFT JOIN r.rolePermissions rp " +
            "LEFT JOIN rp.permission p")
    List<Object[]> findAllUsersWithRolesAndPermissions();

   //Sửa role

    boolean existsByName(String name);





}
