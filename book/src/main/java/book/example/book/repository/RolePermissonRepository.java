package book.example.book.repository;

import book.example.book.enity.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolePermissonRepository extends JpaRepository<RolePermission,Long> {

}
