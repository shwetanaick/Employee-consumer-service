package com.benz.file.service.decrypt.service;

import com.benz.file.service.decrypt.model.Employee;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class ProcessorService {

    @Autowired
    public ObjectMapper mapper;
    @Autowired
    SaveFileService saveFileService;

    @Autowired
    UpdateFileService updateFileService;
    @Autowired
    Employee employee;

private Map<String,Object> message = null;

    public String processFile(Object record)  {
        try {
            message = (Map<String,Object>)mapper.readValue(record.toString(),Map.class);
            employee = mapper.convertValue(message, Employee.class);
            if(message.get("operation").equals("STORE")) {

                if (message.get("fileType").equals("CSV"))
                    saveFileService.saveFileAsCSV(employee);


                else if (message.get("fileType").equals("XML")) {
                    saveFileService.saveFileAsXML(employee);
                }
            }
            else if (message.get("operation").equals("UPDATE")) {
                if (message.get("fileType").equals("CSV"))
                    updateFileService.updateCSVFile(employee);
                else if (message.get("fileType").equals("XML")){
                    updateFileService.updateXMLFile(employee);
                }


            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Record in Kafka "+message.toString());

            return null;
    }
}
