package book.example.book.enity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "app_function_permissions")
public class FunctionPermission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "function_id", nullable = false)
    private Function function;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "permission_id", nullable = false)
    private Permission permission;
}
