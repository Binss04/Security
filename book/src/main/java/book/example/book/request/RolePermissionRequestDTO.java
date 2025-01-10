package book.example.book.request;

import lombok.Data;

import java.util.List;

@Data
public class RolePermissionRequestDTO {
    private String roleName;

    private List<String> permissionNames;
}
