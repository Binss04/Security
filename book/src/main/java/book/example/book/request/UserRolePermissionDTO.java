package book.example.book.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserRolePermissionDTO {
    private String username;
    private List<RoleDTO> roles;

    @Data
    @AllArgsConstructor
    public static class RoleDTO {
        private String roleName;
        private List<String> permissions;
    }


}
