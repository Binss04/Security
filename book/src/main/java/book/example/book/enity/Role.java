package book.example.book.enity;
import jakarta.persistence.*;

import lombok.Data;

import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Data

@Table(name = "app_roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
    private Set<RolePermission> rolePermissions;


}
