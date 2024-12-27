package book.example.book.service;

import book.example.book.enity.Role;
import book.example.book.repository.RoleRepository;
import book.example.book.request.UserRolePermissionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RoleService {
    @Autowired
    private RoleRepository repo;

    public List<UserRolePermissionDTO> getUsersWithRolesAndPermissions() {
        // Lấy kết quả thô từ query
        List<Object[]> results = repo.findAllUsersWithRolesAndPermissions();

        // Nhóm dữ liệu theo username
        Map<String, Map<String, List<String>>> userRolePermissionMap = new HashMap<>();

        for (Object[] row : results) {
            String username = (String) row[0];
            String roleName = (String) row[1];
            String permissionName = (String) row[2]; // Có thể null

            userRolePermissionMap
                    .computeIfAbsent(username, k -> new HashMap<>())
                    .computeIfAbsent(roleName, k -> new ArrayList<>());

            // Thêm permission vào role nếu tồn tại
            if (permissionName != null) {
                userRolePermissionMap.get(username).get(roleName).add(permissionName);
            }
        }

        // Chuyển đổi sang DTO
        return userRolePermissionMap.entrySet().stream()
                .map(userEntry -> new UserRolePermissionDTO(
                        userEntry.getKey(),
                        userEntry.getValue().entrySet().stream()
                                .map(roleEntry -> new UserRolePermissionDTO.RoleDTO(
                                        roleEntry.getKey(),
                                        roleEntry.getValue()
                                ))
                                .collect(Collectors.toList())
                ))
                .collect(Collectors.toList());
    }

    public List<Role> getAllRoles(){
        return (List<Role>) repo.findAll();
    }


}