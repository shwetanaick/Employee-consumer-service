package com.benz.file.service.decrypt.utils;

import com.benz.file.service.decrypt.model.Employee;
import com.benz.file.service.decrypt.model.Employees;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.stereotype.Component;

import javax.xml.bind.*;
import java.io.*;
import java.util.ArrayList;

import static com.benz.file.service.decrypt.service.SaveFileService.CSV_FILE_PATH;
import static com.benz.file.service.decrypt.service.SaveFileService.XML_FILE_PATH;

@Component
public class CommonUtils {

    public boolean checkIfRecordAlreadyExistsInXML(Employees emp, Employee newEmployee) {
        for(Employee empl : emp.getEmployees()){
            if(empl.getId().equals(newEmployee.getId())) {
                System.out.println("Record already exists in XML !! ");
                return true;
            }
        }
        return false;
    }

    public Employees unMarshalXml(FileInputStream xmlFile) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Employees.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            Employees employees = (Employees) jaxbUnmarshaller.unmarshal(xmlFile);
            return employees;
        }  catch (PropertyException e) {
            e.printStackTrace();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void marshalXml(Employees employee, FileOutputStream xmlFile) {
        try {
            JAXBContext contextObj = JAXBContext.newInstance(Employees.class);
            Marshaller marshallerObj = contextObj.createMarshaller();
            marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshallerObj.marshal(employee, xmlFile);
        }  catch (PropertyException e) {
            e.printStackTrace();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public Employee readCSVFile(String id) {

        try {

            CSVReader reader = new CSVReader(new FileReader(CSV_FILE_PATH));
            String[] lineInArray;
            while ((lineInArray = reader.readNext()) != null) {
                Employee employee = new Employee();
                if (lineInArray[4].equals(id)) {
                    employee.setName(lineInArray[0]);
                    employee.setDob(lineInArray[1]);
                    employee.setSalary(lineInArray[2]);
                    employee.setAge(Integer.parseInt(lineInArray[3]));
                    employee.setId(lineInArray[4]);
                    return employee;
                }


            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvValidationException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Employee readXMLFile(String id) {
        try {
            File xmlFile = new File(XML_FILE_PATH);
            Employees emp = new Employees();
            emp.setEmployees(new ArrayList<>());
            FileInputStream xmlInFile;
            FileOutputStream xmlOutFile;
            if (xmlFile.exists()) {

                xmlInFile = new FileInputStream(XML_FILE_PATH);

                emp = unMarshalXml(xmlInFile);
                xmlOutFile = new FileOutputStream(XML_FILE_PATH,false);
                for (Employee empl : emp.getEmployees()) {
                    if (empl.getId().equals(id)) {
                        System.out.println("Record already exists in XML !! ");
                        marshalXml(emp,xmlOutFile);
                        xmlInFile.close();
                        return empl;
                    }
                }
                marshalXml(emp,xmlOutFile);
                xmlInFile.close();
                xmlOutFile.close();
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

