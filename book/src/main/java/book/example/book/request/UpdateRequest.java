package book.example.book.request;

import lombok.Data;

@Data
public class UpdateRequest {
    private Long id;
    private BookSearchRequest bookRequest;
}
