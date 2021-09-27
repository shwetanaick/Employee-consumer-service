package com.benz.file.service.decrypt.service;

import com.benz.file.service.decrypt.model.Employee;
import com.benz.file.service.decrypt.model.Employees;
import com.benz.file.service.decrypt.utils.CommonUtils;
import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.opencsv.exceptions.CsvValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static com.benz.file.service.decrypt.service.SaveFileService.CSV_FILE_PATH;
import static com.benz.file.service.decrypt.service.SaveFileService.XML_FILE_PATH;

@Component
public class UpdateFileService {

    @Autowired
    Employee employee;
    @Autowired
    private CommonUtils utils;


    public void updateCSVFile(Employee newEmployee) {
        List<Employee> contents = new ArrayList<>();
        try {

            CSVReader reader = new CSVReader(new FileReader(CSV_FILE_PATH));
            String[] lineInArray;
            while ((lineInArray = reader.readNext()) != null) {
                Employee employee = new Employee();
                if(lineInArray[4].equals(newEmployee.getId())) {
                    employee.setName(newEmployee.getName());
                    employee.setDob(newEmployee.getDob());
                    employee.setSalary(newEmployee.getSalary());
                    employee.setAge(newEmployee.getAge());
                    employee.setId(newEmployee.getId());

                }
                else{
                    employee.setName(lineInArray[0]);
                    employee.setDob(lineInArray[1]);
                    employee.setSalary(lineInArray[2]);
                    employee.setAge(Integer.parseInt(lineInArray[3]));
                    employee.setId(lineInArray[4]);
                }

            contents.add(employee);

            }
            updateCSVFile(contents);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvValidationException e) {
            e.printStackTrace();
        } catch (CsvRequiredFieldEmptyException e) {
            e.printStackTrace();
        } catch (CsvDataTypeMismatchException e) {
            e.printStackTrace();
        }


    }

    public void updateCSVFile(List<Employee> contentsList) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {

        String[] HEADERS = { "name", "dob", "salary", "age","id"};
        FileWriter fwriter = new FileWriter(CSV_FILE_PATH);

        ColumnPositionMappingStrategy mappingStrategy = new ColumnPositionMappingStrategy();
        mappingStrategy.setType(Employee.class);
        mappingStrategy.setColumnMapping(HEADERS);

        StatefulBeanToCsvBuilder<Employee> builder = new StatefulBeanToCsvBuilder(fwriter);
        StatefulBeanToCsv beanWriter = builder.withMappingStrategy(mappingStrategy).build();

        beanWriter.write(contentsList);

        fwriter.close();

    }

    public void updateXMLFile(Employee newEmployee){
        try {
            FileOutputStream xmlOutFile;
            File xmlFile = new File(XML_FILE_PATH);
            Employees emp = new Employees();
            emp.setEmployees(new ArrayList<>());

            if(xmlFile.exists()) {
                FileInputStream xmlInFile = new FileInputStream(XML_FILE_PATH);
                emp=utils.unMarshalXml(xmlInFile);
                emp = updateXML(emp, newEmployee);
                xmlOutFile = new FileOutputStream(XML_FILE_PATH, false);
                utils.marshalXml(emp, xmlOutFile);

                xmlInFile.close();
                xmlOutFile.close();

            }

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Employees updateXML(Employees emp, Employee newEmployee) {
        Employee e = utils.readXMLFile(newEmployee.getId());

        if(e!=null){
            e.setName(newEmployee.getName());
            e.setAge(newEmployee.getAge());
            e.setDob(newEmployee.getDob());
            e.setSalary(newEmployee.getSalary());
            e.setId(newEmployee.getId());
            emp.getEmployees().removeIf(employee -> employee.getId().equals(e.getId()) );
      }
        else System.out.println("Record Not Found!!");
        emp.getEmployees().add(e);
        System.out.println("Record Updated !!");
        return emp;
    }


}
