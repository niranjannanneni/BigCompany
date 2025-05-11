package com.bigcompany;

import java.io.*;
import java.util.*;

public class CSVReader {
    public static Map<String, Employee> readEmployees(String filePath) throws IOException {
        Map<String, Employee> employees = new HashMap<>();
        List<String[]> rawRecords = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); 
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                rawRecords.add(parts);
            }
        }
        for (String[] record : rawRecords) {
            String empId = record[0].trim();
            String firstName = record[1].trim();
            String lastName = record[2].trim();
            double salary = Double.parseDouble(record[3].trim());
            String managerId = record.length > 4 ? record[4].trim() : null;

            employees.put(empId, new Employee(empId, firstName, lastName, managerId,salary));
        }


        for (Employee emp : employees.values()) {
            if (emp.getManagerId() != null && employees.containsKey(emp.getManagerId())) {
                employees.get(emp.getManagerId()).addSubordinate(emp);
            }
        }

        return employees;
    }
}
