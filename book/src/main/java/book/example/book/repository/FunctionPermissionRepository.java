package book.example.book.repository;

import book.example.book.enity.FunctionPermission;
import book.example.book.request.FunctionPermissionDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FunctionPermissionRepository extends JpaRepository<FunctionPermission,Long> {
    @Query("SELECT new book.example.book.request.FunctionPermissionDTO(fp.function.name, fp.permission.name) " +
            "FROM FunctionPermission fp")
    List<FunctionPermissionDTO> fetchFunctionPermissions();
}
