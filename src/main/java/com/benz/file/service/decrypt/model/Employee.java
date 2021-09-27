package com.benz.file.service.decrypt.model;

import com.opencsv.bean.CsvBindByName;
import org.springframework.stereotype.Component;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@Component
@XmlRootElement(name = "employee")
@XmlAccessorType (XmlAccessType.FIELD)
public class Employee {
    @CsvBindByName
    private String name;
    @CsvBindByName
    private String dob;
    @CsvBindByName
    private String salary;
    @CsvBindByName
    private int age;
    @CsvBindByName
    private String id;
    private List<Employee> employees = new ArrayList<>();


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


}

