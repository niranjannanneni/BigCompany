package com.bigcompany;

import java.util.*;

public class EmployeeAnalyzer {
    private Map<String, Employee> employees;


    public EmployeeAnalyzer(Map<String, Employee> employees) {
        this.employees = employees;
    }


    public void analyzeSalaries() {
        for (Employee manager : employees.values()) {
            if (!manager.getSubordinates().isEmpty()) {
                double avgSalary = manager.getSubordinates().stream().mapToDouble(Employee::getSalary).average().orElse(0);
                double minThreshold = avgSalary * 1.2;
                double maxThreshold = avgSalary * 1.5;

                if (manager.getSalary() < minThreshold) {
                    System.out.printf("Manager %s (ID: %s) earns %.2f less than they should.\n",
                            manager.getFirstName(), manager.getEmpId(), minThreshold - manager.getSalary());
                } else if (manager.getSalary() > maxThreshold) {
                    System.out.printf("Manager %s (ID: %s) earns %.2f more than they should.\n",
                            manager.getFirstName(), manager.getEmpId(), manager.getSalary() - maxThreshold);
                }
            }
        }
    }

    public void analyzeReportingLines() {
        for (Employee emp : employees.values()) {
            int depth = calculateDepth(emp);
            if (depth > 4) {
                System.out.printf("Employee %s (ID: %s) has a reporting line too long by %d levels.\n",
                        emp.getFirstName(), emp.getEmpId(), depth - 4);
            }
        }
    }

    public int calculateDepth(Employee emp) {
        int depth = 0;
        while (emp.getManagerId() != null && employees.containsKey(emp.getManagerId())) {
            emp = employees.get(emp.getManagerId());
            depth++;
        }
        return depth;
    }
}
