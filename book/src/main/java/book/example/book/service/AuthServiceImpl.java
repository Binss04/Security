package book.example.book.service;

import book.example.book.enity.DefaultRoles;
import book.example.book.enity.Role;
import book.example.book.enity.User;
import book.example.book.jwt.JwtTokenProvider;
import book.example.book.repository.RoleRepository;
import book.example.book.repository.UserRepository;
import book.example.book.request.AuthResponseDTO;
import book.example.book.request.LoginDTO;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService{
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserRepository repository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public String login(LoginDTO loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsername(),
                loginDto.getPassword()
        ));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.generateToken(authentication);
       return  token;
    }

    @Override
    public User register(String username, String password ){
        Optional<User> existingUser =  repository.findByUsername(username);
        if (existingUser.isPresent()) {
            throw new RuntimeException("Username already exists!");
        }

        Role userRole = roleRepository.findByName(DefaultRoles.ROLE_USER.name())
                .orElseThrow(() -> new RuntimeException("Default tole USER not found!"));

        User newUser = User.builder()
                .username(username)
                .password(passwordEncoder.encode(password)) // Mã hóa mật khẩu
                .roles(new HashSet<>(Set.of(userRole)))
                .build();

        return repository.save(newUser);
    }



}
