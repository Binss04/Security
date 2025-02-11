package book.example.book.response;

import book.example.book.enity.Permission;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class FunctionResponseDTO {
    private List<PermissionResponseDTO> functions;
}
