package book.example.book.service;

import book.example.book.enity.Role;
import book.example.book.enity.User;
import book.example.book.repository.RoleRepository;
import book.example.book.repository.UserRepository;
import book.example.book.request.UserRolePermissionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RoleService {
    @Autowired
    private RoleRepository repo;

    @Autowired
    private UserRepository userRepository;

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


    //Sửa role
   public User updateRoleByUsername(String username, String roleName){
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username:" + username));
        Role role = repo.findByName(roleName)
                .orElseThrow(() -> new RuntimeException("Role not found with name: " + roleName));

       Set<Role> currentRoles = user.getRoles();
       currentRoles.clear();
       currentRoles.add(role);
       user.setRoles(currentRoles);

        return userRepository.save(user);
   }

    //thêm role
    public Role addRole(Role role) {
        if(role.getName() == null || role.getName().trim().isEmpty()){
            throw new IllegalArgumentException("Tên role không được để trống");
        }
        if (repo.existsByName(role.getName())){
            throw new IllegalArgumentException("bị trùng tên");
        }
        return repo.save(role);
    }





}