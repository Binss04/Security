package book.example.book.controller;

import book.example.book.enity.Permission;
import book.example.book.request.PermissionDTO;
import book.example.book.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/permission")
@CrossOrigin(origins = "http://localhost:4200")

public class PermissionController {
    @Autowired
    private PermissionService service;
    @PostMapping("/getAll")
    public ResponseEntity<List<Permission>> getAllPermissions(@RequestBody PermissionDTO usernameRequest) {
        String username = usernameRequest.getUsername();
        List<Permission> permissions = service.getAll(username);
        return ResponseEntity.ok(permissions);
    }
}
