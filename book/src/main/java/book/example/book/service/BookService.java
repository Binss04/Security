package book.example.book.service;

import book.example.book.enity.Book;
import book.example.book.request.BookSearchRequest;
import book.example.book.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
    public List<Book> getAllBooks() {
        return bookRepository.findAllBooks();
    }

    public List<Book> insertBook(BookSearchRequest bookRequest) {
        // Chèn sách vào CSDL
        bookRepository.insertBook(
                bookRequest.getAuthor(),
                bookRequest.getGenre(),
                bookRequest.getLanguage(),
                bookRequest.getPagecount(),
                bookRequest.getPrice(),
                bookRequest.getPublish_date(),
                bookRequest.getPublisher(),
                bookRequest.getSynopsis(),
                bookRequest.getTitle()
        );

        // Lấy danh sách sách sau khi thêm
        return bookRepository.findAllBooks();
    }

    public void deleteBook(Long id) {
        bookRepository.deleteBook(id);
    }

    public List<Book> updateBook(Long id, BookSearchRequest bookRequest) {
        bookRepository.updateBook(
                id,
                bookRequest.getAuthor(),
                bookRequest.getGenre(),
                bookRequest.getLanguage(),
                bookRequest.getPagecount(),
                bookRequest.getPrice(),
                bookRequest.getPublish_date(),
                bookRequest.getPublisher(),
                bookRequest.getSynopsis(),
                bookRequest.getTitle()
        );
        return bookRepository.findAllBooks();
    }


    public Map<String, Object> searchBooks(BookSearchRequest bookRequest) {
        return bookRepository.searchBooks(
                bookRequest.getAuthor(),
                bookRequest.getGenre(),
                bookRequest.getLanguage(),
                bookRequest.getStartDate(),
                bookRequest.getEndDate(),
                bookRequest.getPageNo(),
                bookRequest.getPageSize()
        );
    }

}
