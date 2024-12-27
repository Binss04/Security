package book.example.book.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FunctionPermissionDTO {
    private String functionName;
    private String permissionName;
}
