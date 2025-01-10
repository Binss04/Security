package book.example.book.request;

import lombok.Data;

@Data
public class UpdateUserRoleRequest {
    private String username; // Tên user cần sửa
    private String role;
}
