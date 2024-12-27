package book.example.book.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Date;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookSearchRequest {
    @Size(max = 100, message = "Title cannot exceed 100 characters")
    private String title;

    @Size(max = 100, message = "Author name cannot exceed 100 characters")
    private String author;

    @Size(max = 50, message = "Genre cannot exceed 50 characters")
    private String genre;

    @Size(max = 50, message = "Language cannot exceed 50 characters")
    private String language;

    @Size(max = 100, message = "Publisher name cannot exceed 100 characters")
    private String publisher;

    @Min(value = 0, message = "Page count cannot be negative")
    private Integer pagecount;

    @DecimalMin(value = "0", message = "Price cannot be negative")
    private Integer price;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date publish_date;

    @Size(max = 500, message = "Synopsis cannot exceed 500 characters")
    private String synopsis;

    @DecimalMin(value = "0", message = "Minimum price cannot be negative")
    private Integer minPrice;

    @DecimalMin(value = "0", message = "Maximum price cannot be negative")
    private Integer maxPrice;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date endDate;

    @Min(value = 1, message = "Page number must be at least 1")
    private int pageNo = 1;

    @Min(value = 1, message = "Page size must be at least 1")
    private int pageSize = 10;
}
