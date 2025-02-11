package book.example.book.response;

import book.example.book.enity.Function;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class RoleResponseDTO {
    private List<FunctionResponseDTO> roles;
}
