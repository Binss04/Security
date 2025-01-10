package book.example.book.service;

import book.example.book.enity.Permission;
import book.example.book.enity.Role;
import book.example.book.enity.RolePermission;
import book.example.book.repository.PermissionRepository;
import book.example.book.repository.RolePermissonRepository;
import book.example.book.repository.RoleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RolePermissionService {
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private RolePermissonRepository rolePermissionRepository;


    public List<RolePermission> addRolePermissions(String roleName, List<String> permissionNames) {
        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy Role: " + roleName));
        List<RolePermission> rolePermissions = new ArrayList<>();
        for (String permissionName : permissionNames) {
            Permission permission = permissionRepository.findByName(permissionName)
                    .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy Permission: " + permissionName));
            // Kiểm tra sự kết hợp role và permission đã tồn tại chưa
            boolean exists = rolePermissionRepository.existsByRoleAndPermission(role, permission);
            if (exists) {
                throw new IllegalArgumentException("RolePermission đã tồn tại: " + roleName + " - " + permissionName);
            }
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRole(role);
            rolePermission.setPermission(permission);
            rolePermissions.add(rolePermissionRepository.save(rolePermission));
        }
        return rolePermissions;
    }


//    public void deleteByRoleNameAndPermissionNames(String roleName, List<String> permissionNames) {
//        rolePermissionRepository.deleteByRoleNameAndPermissionNames(roleName, permissionNames);
//    }

    @Transactional
    public void deleteRolePermissions(String roleName, List<String> permissionNames) {
        if (roleName == null || roleName.isEmpty() || permissionNames == null || permissionNames.isEmpty()) {
            throw new IllegalArgumentException("RoleName và PermissionNames không được null hoặc rỗng");
        }

        rolePermissionRepository.deleteByRoleNameAndPermissionNameIn(roleName, permissionNames);
    }



    public List<String> getPermissionsByRole(String roleName) {
        return rolePermissionRepository.findPermissionsByRoleName(roleName);
    }

}
