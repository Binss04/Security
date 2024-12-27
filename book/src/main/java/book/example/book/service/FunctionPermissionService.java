package book.example.book.service;

import book.example.book.enity.FunctionPermission;
import book.example.book.repository.FunctionPermissionRepository;
import book.example.book.request.FunctionPermissionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FunctionPermissionService {

    @Autowired
    private FunctionPermissionRepository repo;


    public List<FunctionPermission> getAllPermission(){
        return repo.findAll();
    }


    public Map<String, List<String>> getFunctionPermissions() {
        List<FunctionPermissionDTO> dtoList = repo.fetchFunctionPermissions();
        return dtoList.stream()
                .collect(Collectors.groupingBy(
                        FunctionPermissionDTO::getFunctionName,
                        Collectors.mapping(FunctionPermissionDTO::getPermissionName, Collectors.toList()) // Lấy danh sách Permission name
                ));
    }

}
