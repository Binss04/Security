package book.example.book.controller;

import book.example.book.enity.*;
import book.example.book.request.*;
import book.example.book.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;
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
    private FunctionService funcService;

    @Autowired
    private RolePermissionService rolePermissionService;

    @Autowired
    private RoleFunctionService roleFunctionService;

    @Autowired UserService userService;

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

    @PostMapping("/permissions")
    public ResponseEntity<List<String>> getPermissionsByRole(@RequestBody Map<String, String> request) {
        String roleName = request.get("roleName");
        if (roleName == null || roleName.isEmpty()) {
            return ResponseEntity.badRequest().body(Collections.emptyList());
        }
        List<String> permissions = rolePermissionService.getPermissionsByRole(roleName);
        return ResponseEntity.ok(permissions);
    }


    @PostMapping("/role-function")
    public ResponseEntity<List<String>> getFucntionsByRole(@RequestBody Map<String, String> request) {
        String roleName = request.get("roleName");
        if (roleName == null || roleName.isEmpty()) {
            return ResponseEntity.badRequest().body(Collections.emptyList());
        }
        List<String> permissions = roleFunctionService.getFunctionsByRoleName(roleName);
        return ResponseEntity.ok(permissions);
    }


//    @GetMapping("/names")
//    public List<FunctionDTO> getAllFunctionNames() {
//        return funcService.getAllFunctions();
//    }

//    @GetMapping("/all-role-function")
//    public Map<String, List<String>> getAllRoleFunctions() {
//        return roleFunctionService.getAllRoleFunctions();
//    }


    //lấy role, funciton theo username
    @PostMapping("/roles-functions-permissions")
    public Map<String,List<Map<String, String>>> getRolesFunctionsPermissions(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        return userService.getRolesFunctionsByUsername(username);
    }

    //thêm role-function
    @PostMapping("/add-role-function")
    public ResponseEntity<String> addFunctionsToRole(@RequestBody RoleFunctionRequest requestDTO) {
        System.out.println("a");
        String message = roleFunctionService.addFunctionRole(requestDTO.getRoleName(), requestDTO.getFunctionNames());
        return ResponseEntity.ok(message);
    }



    //xóa role-function
    @DeleteMapping("/delete-role-function")
    public List<String> deleteRolePermissions(@RequestBody RoleFunctionRequest request) {
        System.out.println("adddd");
        try {
            roleFunctionService.deleteRoleFunction(request.getRoleName(), request.getFunctionNames());
            return request.getFunctionNames();
        } catch (Exception e) {
            throw new RuntimeException("Lỗi: " + e.getMessage());
        }
    }



}
