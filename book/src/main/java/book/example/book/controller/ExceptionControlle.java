package book.example.book.controller;

import book.example.book.enity.Permission;
import book.example.book.enity.Role;
import book.example.book.request.FunctionPermissionDTO;
import book.example.book.service.FunctionPermissionService;
import book.example.book.service.FunctionService;
import book.example.book.service.PermissionService;
import book.example.book.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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


}
