package book.example.book.request;

import lombok.Data;

import java.util.List;

@Data
public class RoleFunctionRequest {
    private String roleName;
    private List<String> functionNames;
}
