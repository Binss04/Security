package book.example.book.controller;

import book.example.book.enity.Permission;
import book.example.book.enity.Role;
import book.example.book.enity.RolePermission;
import book.example.book.enity.User;
import book.example.book.request.RolePermissionDeleteRequest;
import book.example.book.request.RolePermissionRequestDTO;
import book.example.book.request.UpdateRequest;
import book.example.book.request.UpdateUserRoleRequest;
import book.example.book.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/exception")
@Validated
@CrossOrigin(origins = "http://localhost:4200")
public class ExceptionControlle {

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private FunctionPermissionService functionService;

    @Autowired
    private RolePermissionService rolePermissionService;

    @GetMapping("/all")
    public List<String> getAllRoles() {
        return roleService.getAllRoles().stream()
                .map(Role::getName) // Assuming Role has a "name" field
                .collect(Collectors.toList());
    }

    @GetMapping("/allpermission")
    public List<String> getAllRolesPermission() {
        return permissionService.getAllPermission().stream()
                .map(Permission::getName) // Assuming Role has a "name" field
                .collect(Collectors.toList());
    }

    //getAllFunciton và permission
    @GetMapping("/getAll")
    public Map<String, List<String>> getFunctionPermissions() {
        return functionService.getFunctionPermissions();
    }

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

//    @DeleteMapping("/delete")
//    public ResponseEntity<Void> deleteByRoleNameAndPermissionNames(@RequestBody RolePermissionDeleteRequest request) {
//        rolePermissionService.deleteByRoleNameAndPermissionNames(request.getRoleName(), request.getPermissionNames());
//        return ResponseEntity.noContent().build();
//    }

    @DeleteMapping("/delete")
    public List<String> deleteRolePermissions(@RequestBody RolePermissionDeleteRequest request) {
        try {
            rolePermissionService.deleteRolePermissions(request.getRoleName(), request.getPermissionNames());
            return request.getPermissionNames();
        } catch (Exception e) {
            throw new RuntimeException("Lỗi: " + e.getMessage());
        }
    }


    @PostMapping("/permissions")
    public ResponseEntity<List<String>> getPermissionsByRole(@RequestBody Map<String, String> request) {
        String roleName = request.get("roleName");
        if (roleName == null || roleName.isEmpty()) {
            return ResponseEntity.badRequest().body(Collections.emptyList());
        }

        List<String> permissions = rolePermissionService.getPermissionsByRole(roleName);
        return ResponseEntity.ok(permissions);
    }

@PutMapping("/update-role")
    public ResponseEntity<User> updateUserRole(@RequestBody UpdateUserRoleRequest request){
        User updateUser = roleService.updateRoleByUsername(request.getUsername(), request.getRole() );
                return ResponseEntity.ok(updateUser);
}

}
