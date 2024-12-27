package book.example.book.controller;

import book.example.book.enity.User;
import book.example.book.request.AuthResponseDTO;
import book.example.book.request.LoginDTO;
import book.example.book.service.AuthService;
import book.example.book.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")

public class AuthController {
    @Autowired
    private AuthService authService;

    // Build Login REST API
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginDTO loginDto){
        String token = authService.login(loginDto);
        AuthResponseDTO authResponseDto = new AuthResponseDTO();
        authResponseDto.setAccessToken(token);

        return new ResponseEntity<>(authResponseDto, HttpStatus.OK);
    }

    //đăng kí
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody LoginDTO loginDTO) {
        try {
            User registerdUser = authService.register(loginDTO.getUsername(), loginDTO.getPassword());
            return ResponseEntity.ok("User registered successfully!");
        }catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }




}
