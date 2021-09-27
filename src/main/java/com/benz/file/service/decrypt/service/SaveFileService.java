package com.benz.file.service.decrypt.service;

import com.benz.file.service.decrypt.model.Employee;
import com.benz.file.service.decrypt.model.Employees;
import com.benz.file.service.decrypt.utils.CommonUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import com.opencsv.exceptions.CsvValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.xml.bind.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class SaveFileService {
    @Autowired
    public CommonUtils utils;

    public static final String CSV_FILE_PATH = "./src/main/resources/File.csv";
    public static final String XML_FILE_PATH = "./src/main/resources/File.xml";


    public void saveFileAsCSV(Employee message)  {
        List<Employee> list = new ArrayList<>();
        String[] HEADERS = { "name", "dob", "salary", "age","id"};
        int flag=0;
        try {
            FileWriter fwriter = new FileWriter(CSV_FILE_PATH, true);
            list.add(message);

            CSVReader reader = new CSVReader(new FileReader(CSV_FILE_PATH));
            String[] lineInArray;
            while (true) {

                if (!((lineInArray = reader.readNext()) != null)) break;

                Employee employee = new Employee();
                if (lineInArray[4].equals(message.getId())) {
                    flag=1;
                }
            }
            if(flag==0) {
                ColumnPositionMappingStrategy mappingStrategy = new ColumnPositionMappingStrategy();
                mappingStrategy.setType(Employee.class);
                mappingStrategy.setColumnMapping(HEADERS);

                StatefulBeanToCsvBuilder<Employee> builder = new StatefulBeanToCsvBuilder(fwriter);
                StatefulBeanToCsv beanWriter = builder.withMappingStrategy(mappingStrategy).build();

                beanWriter.write(list);

                fwriter.close();

            }
            else System.out.println("Data already present in file");

        }catch (CsvValidationException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvRequiredFieldEmptyException e) {
            e.printStackTrace();
        } catch (CsvDataTypeMismatchException e) {
            e.printStackTrace();
        }
    }



    public void saveFileAsXML(Employee newEmployee)  {

        try {
            FileOutputStream xmlOutFile;
            File xmlFile = new File(XML_FILE_PATH);
            Employees emp = new Employees();
            emp.setEmployees(new ArrayList<Employee>());



            if(!xmlFile.exists()){
                emp.getEmployees().add(newEmployee);
                xmlOutFile = new FileOutputStream(XML_FILE_PATH,false);
                utils.marshalXml(emp,xmlOutFile);
            }
            else {

                FileInputStream xmlInFile = new FileInputStream(XML_FILE_PATH);
                emp = utils.unMarshalXml(xmlInFile);
                if(!utils.checkIfRecordAlreadyExistsInXML(emp,newEmployee)) {
                    emp.getEmployees().add(newEmployee);
                    xmlOutFile = new FileOutputStream(XML_FILE_PATH, false);
                    utils.marshalXml(emp, xmlOutFile);
                    xmlInFile.close();
                    xmlOutFile.close();
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
