package book.example.book.enity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Table(name = "app_function")
public class Function {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String url;


    @OneToMany(mappedBy = "function", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<FunctionPermission> functionPermissions;





}
