package book.example.book.request;

import lombok.Data;

import java.util.List;

@Data
public class RolePermissionDeleteRequest {
    private String roleName;  // Tên của role
    private List<String> permissionNames;
}
