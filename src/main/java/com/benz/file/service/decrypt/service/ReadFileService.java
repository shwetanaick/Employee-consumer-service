package com.benz.file.service.decrypt.service;

import com.benz.file.service.decrypt.model.Employee;
import com.benz.file.service.decrypt.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReadFileService {

    @Autowired
    private CommonUtils utils;

    public Employee readFile(String id){
        Employee employee;
        employee = utils.readCSVFile(id);
        if(employee!=null)
            return employee;
        else {
            employee=utils.readXMLFile(id);
            if(employee!=null)
                return employee;
        }

        return null;

    }

    }
