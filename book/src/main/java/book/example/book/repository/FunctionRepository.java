package book.example.book.repository;

import book.example.book.enity.Function;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface FunctionRepository extends JpaRepository<Function,Long> {

}
