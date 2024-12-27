package book.example.book.controller;

import book.example.book.enity.Book;
import book.example.book.enity.Permission;
import book.example.book.enity.Role;
import book.example.book.request.BookSearchRequest;
import book.example.book.request.UpdateRequest;
import book.example.book.request.UserRolePermissionDTO;
import book.example.book.service.BookService;

import book.example.book.service.PermissionService;
import book.example.book.service.RoleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/book")
@Validated
@CrossOrigin(origins = "http://localhost:4200")
public class BookController {
    private final BookService bookService;

    @Autowired
    private RoleService roleService;





    public BookController(BookService bookService) {
        this.bookService = bookService;
    }
    @GetMapping("/getAll")
    public Map<String, Object> getBooks() {
        List<Book> books = bookService.getAllBooks();
        Map<String, Object> response = new HashMap<>();
        response.put("books", books);
        return response;
    }

    @PostMapping("/insert")
    public ResponseEntity<List<Book>> insertBook(@RequestBody BookSearchRequest bookRequest) {
        List<Book> books = bookService.insertBook(bookRequest);
        System.out.println(books);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }



//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity<?> deleteBook(@PathVariable Long id) {
//        System.out.println("ok");
//        bookService.deleteBook(id);
//        List<Book> books = bookService.getAllBooks();
//            return ResponseEntity.ok(books);
//        }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteBook(@RequestBody Map<String, Long> requestBody) {
        // Lấy id từ request body
        Long id = requestBody.get("id");
        if (id == null) {
            return ResponseEntity.badRequest().body("Id is required");
        }
        // In ra log để kiểm tra
        System.out.println("Deleting book with id: " + id);
        bookService.deleteBook(id);
        List<Book> books = bookService.getAllBooks();
        return ResponseEntity.ok(books);
    }




    @PutMapping("/update")
    public ResponseEntity<Map<String, Object>> updateBook(@RequestBody UpdateRequest requestBody) {
        Long id = requestBody.getId();
        BookSearchRequest bookRequest = requestBody.getBookRequest();
        List<Book> updatedBooks = bookService.updateBook(id, bookRequest);
        Map<String, Object> response = new HashMap<>();
        response.put("bookRequest", updatedBooks.get(0)); // Giả sử chỉ trả về một sách đã được cập nhật

        return ResponseEntity.ok(response);
    }


    @PostMapping("/search")
    public Map<String, Object> searchBooks(@Validated @RequestBody BookSearchRequest searchRequest) {
        return bookService.searchBooks(searchRequest);
    }

    @GetMapping("/roles-with-permissions")
    public List<UserRolePermissionDTO> getUsersWithRolesAndPermissions() {
        return roleService.getUsersWithRolesAndPermissions();
    }




}
