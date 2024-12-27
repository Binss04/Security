package book.example.book.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PermissionRoleDTO {
    private String roleName;
    private List<String> permissions;
}
