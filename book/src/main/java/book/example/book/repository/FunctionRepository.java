package book.example.book.repository;

import book.example.book.enity.Function;
import book.example.book.enity.Permission;
import book.example.book.request.FunctionDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FunctionRepository extends JpaRepository<Function,Long> {

    Optional<Function> findByName(String name);


    @Query("SELECT DISTINCT p FROM User u " +
            "JOIN u.roles r " +
            "JOIN  r.roleFunctions rp  " +
            "JOIN rp.function p " +
            "WHERE u.username = :username")
    List<Function> findFunctionByUsername(String username);

//    @Query("SELECT new book.example.book.request.FunctionDTO(f.id, f.name, f.url) FROM Function f")
//    List<FunctionDTO> getAllFunctions();




}
