package com.benz.file.service.decrypt.controller;

import com.benz.file.service.decrypt.model.Employee;
import com.benz.file.service.decrypt.service.ReadFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/file")

public class ReadController {

    @Autowired
    ReadFileService readFileService;
    @GetMapping("/read/{id}")
    public Employee readFile(@PathVariable String id ){
        return readFileService.readFile(id);
    }

}
