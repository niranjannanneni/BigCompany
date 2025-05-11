package com.bigcompany;

import java.io.IOException;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        String filePath = "src/main/resources/employees.csv";
        try {
            Map<String, Employee> employees = CSVReader.readEmployees(filePath);
            EmployeeAnalyzer analyzer = new EmployeeAnalyzer(employees);

            System.out.println("---------Salary Analysis-------");
            analyzer.analyzeSalaries();

            System.out.println("\n---------Reporting Line Analysis-------");
            analyzer.analyzeReportingLines();
        } catch (IOException e) {
            System.err.println("Error csv file: " + e.getMessage());
        }
    }
}
