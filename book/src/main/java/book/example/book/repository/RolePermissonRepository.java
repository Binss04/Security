package book.example.book.repository;

import book.example.book.enity.Permission;
import book.example.book.enity.Role;
import book.example.book.enity.RolePermission;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RolePermissonRepository extends JpaRepository<RolePermission,Long> {
    boolean existsByRoleAndPermission(Role role, Permission permission);

//    @Modifying
//    @Query("DELETE FROM RolePermission rp WHERE rp.role.name = :roleName AND rp.permission.name IN :permissionNames")
//    void deleteByRoleNameAndPermissionNames(String roleName, List<String> permissionNames);

    void deleteByRoleNameAndPermissionNameIn(String roleName, List<String> permissionNames);




    @Query("SELECT rp.permission.name FROM RolePermission rp WHERE rp.role.name = :roleName")
    List<String> findPermissionsByRoleName(@Param("roleName") String roleName);
}
