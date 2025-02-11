package book.example.book.repository;

import book.example.book.enity.Function;
import book.example.book.enity.Permission;
import book.example.book.enity.Role;
import book.example.book.enity.RoleFunction;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RoleFunctionRepository extends JpaRepository<RoleFunction,Long> {
//    @Query("SELECT rf.role.name, rf.function.name FROM RoleFunction rf")
//    List<Object[]> findAllRoleFunctions();

    @Modifying
    @Transactional
    @Query("DELETE FROM RoleFunction rf WHERE rf.role.id IN (SELECT r.id FROM Role r WHERE r.name = :roleName) AND rf.function.id IN (SELECT f.id FROM Function f WHERE f.name IN :functionNames)")
    void deleteByRoleNameAndFunctionNames(@Param("roleName") String roleName, @Param("functionNames") List<String> functionNames);


    void deleteByRoleNameAndFunctionIn(String roleName, List<String> functionNames);


    @Query("SELECT rf.function.name FROM RoleFunction rf WHERE rf.role.name = :roleName")
    List<String> findFunctionsByRoleName(@Param("roleName") String roleName);

}
