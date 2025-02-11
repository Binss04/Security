package book.example.book.response;

import book.example.book.enity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserResponseDTO {
    private String name;
    private RoleResponseDTO role;

    public UserResponseDTO() {
    }
}
