package book.example.book.enity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "book")

public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "author")
    String author;
    @Column(name = "price")
    Integer price;
    @Column(name = "publish_date")
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    Date publish_date;
    @Column(name = "title")
    String title;

    @Column(name = "genre")
    String genre;

    @Column(name = "language")
    String language;

    @Column(name = "publisher")
    String publisher;

    @Column(name = "pagecount")
    Integer pagecount;

    @Column(name = "synopsis")
    String synopsis;
}
