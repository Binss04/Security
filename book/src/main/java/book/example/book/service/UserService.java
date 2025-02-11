package book.example.book.service;

import book.example.book.enity.User;
import book.example.book.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Map<String, List<Map<String, String>>> getRolesFunctionsByUsername(String username) {
        List<Object[]> rawData = userRepository.findRolesAndFunctionsByUsername(username);

        Map<String, List<Map<String, String>>> result = new HashMap<>();

        for (Object[] row : rawData) {
            String role = (String) row[0];
            String functionName = (String) row[1];
            String functionUrl = (String) row[2];

            // Nếu role chưa có trong map, thêm mới
            result.putIfAbsent(role, new ArrayList<>());
            List<Map<String, String>> functionList = result.get(role);

            // Thêm function vào danh sách của role
            Map<String, String> functionData = new HashMap<>();
            functionData.put("name", functionName);
            functionData.put("url", functionUrl);

            functionList.add(functionData);
        }

        return result;
    }

}
