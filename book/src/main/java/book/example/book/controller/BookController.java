package book.example.book.controller;

import book.example.book.enity.*;
import book.example.book.request.*;
import book.example.book.service.BookService;

import book.example.book.service.PermissionService;
import book.example.book.service.RolePermissionService;
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

    @Autowired
    private RolePermissionService rolePermissionService;

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
   //thêm role
    @PostMapping("/add-role")
    public ResponseEntity<?>  addRole(@RequestBody Role role) {
        try {
            Role newrole = roleService.addRole(role);
            return ResponseEntity.ok(newrole);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    //sửa role gán với user
    @PutMapping("/update-role")
    public ResponseEntity<User> updateUserRole(@RequestBody UpdateUserRoleRequest request){
        User updateUser = roleService.updateRoleByUsername(request.getUsername(), request.getRole() );
        return ResponseEntity.ok(updateUser);
    }
    //thêm permission
    @PostMapping("/add")
    public ResponseEntity<List<RolePermission>> addRolePermissions(
            @RequestBody RolePermissionRequestDTO requestDTO) {
        try {
            List<RolePermission> rolePermissions = rolePermissionService.addRolePermissions(
                    requestDTO.getRoleName(),
                    requestDTO.getPermissionNames()
            );
            return ResponseEntity.ok(rolePermissions);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null); // Trả về Bad Request nếu có lỗi
        }
    }
   //xóa permission
    @DeleteMapping("/delete-permission")
    public List<String> deleteRolePermissions(@RequestBody RolePermissionDeleteRequest request) {
        try {
            rolePermissionService.deleteRolePermissions(request.getRoleName(), request.getPermissionNames());
            return request.getPermissionNames();
        } catch (Exception e) {
            throw new RuntimeException("Lỗi: " + e.getMessage());
        }
    }

}
