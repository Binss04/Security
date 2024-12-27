package book.example.book.service;

import book.example.book.enity.Permission;
import book.example.book.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PermissionService {
    @Autowired
    private PermissionRepository repo;
    public List<Permission> getAll(String username){
        return repo.findPermissionsByUsername(username);
    }

    public List<Permission> getAllPermission(){
        return repo.findAll();
    }
}
