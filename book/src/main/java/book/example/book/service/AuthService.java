package book.example.book.service;

import book.example.book.enity.User;
import book.example.book.request.LoginDTO;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    String login(LoginDTO loginDto);

    User register(String username, String password);


}
