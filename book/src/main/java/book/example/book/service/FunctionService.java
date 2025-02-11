package book.example.book.service;

import book.example.book.enity.Function;
import book.example.book.repository.FunctionPermissionRepository;
import book.example.book.repository.FunctionRepository;
import book.example.book.request.FunctionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class FunctionService {

    @Autowired
    FunctionRepository functionRepository;
//    public List<FunctionDTO> getAllFunctions() {
//        return functionRepository.getAllFunctions();
//    }

}
