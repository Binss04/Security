package book.example.book.service;

import book.example.book.enity.*;
import book.example.book.repository.FunctionRepository;
import book.example.book.repository.RoleFunctionRepository;
import book.example.book.repository.RoleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RoleFunctionService {
    @Autowired
    private RoleFunctionRepository repository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private FunctionRepository functionRepository;

//    public Map<String, List<String>> getAllRoleFunctions() {
//        List<Object[]> results = repository.findAllRoleFunctions();
//        Map<String, List<String>> roleFunctionMap = new HashMap<>();
//
//        for (Object[] row : results) {
//            String roleName = (String) row[0];
//            String functionName = (String) row[1];
//
//            roleFunctionMap.computeIfAbsent(roleName, k -> new ArrayList<>()).add(functionName);
//        }
//
//        return roleFunctionMap;
//    }
    //thêm function theo tên
//@Transactional
public String addFunctionRole(String roleName, List<String> functionNames) {
    Optional<Role> roleOptional = roleRepository.findByName(roleName);
    if (roleOptional.isEmpty()) {
        return "Role not found!";
    }
    Role role = roleOptional.get();

    for (String functionName : functionNames) {
        Optional<Function> functionOptional = functionRepository.findByName(functionName);
        if (functionOptional.isPresent()) {
            RoleFunction roleFunction = new RoleFunction();
            roleFunction.setRole(role);
            roleFunction.setFunction(functionOptional.get());
            repository.save(roleFunction);
        }
    }
    return "Functions added to role successfully!";
}


    //xóa function theo tên
    @Transactional
    public void deleteRoleFunction(String roleName, List<String> functionNames) {
        if (roleName == null || roleName.isEmpty() || functionNames == null || functionNames.isEmpty()) {
            throw new IllegalArgumentException("RoleName và PermissionNames không được null hoặc rỗng");
        }

        repository.deleteByRoleNameAndFunctionNames(roleName, functionNames);
    }


    public List<String> getFunctionsByRoleName(String roleName) {
        return repository.findFunctionsByRoleName(roleName);
    }

}
