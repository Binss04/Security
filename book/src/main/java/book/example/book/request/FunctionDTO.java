package book.example.book.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FunctionDTO {
    private Long id;
    private String name;
    private String url;

}
