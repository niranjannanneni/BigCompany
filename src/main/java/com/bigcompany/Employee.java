package com.bigcompany;

import java.util.HashSet;
import java.util.Set;

public class Employee {
    private String empId;
    private String firstName;
    private String lastName;
    private double salary;
    private String managerId;
    private Set<Employee> subordinates = new HashSet<>();

    public Employee(String empId, String firstName, String lastName, String managerId, double salary) {
        this.empId = empId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
        this.managerId = managerId;
    }

    public Set<Employee> getSubordinates() {
        return subordinates;
    }

    public void addSubordinate(Employee subordinate) {
        this.subordinates.add(subordinate);
    }

    public String getEmpId() {
        return empId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public double getSalary() {
        return salary;
    }
    
    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getManagerId() {
        return managerId;
    }
}