package book.example.book.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data

public class AuthResponseDTO {
    private String accessToken;

}
