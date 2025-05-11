package com.bigcompany;

import org.junit.jupiter.api.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class EmployeeAnalyzerTest {

    private Employee ceo;
    private Employee manager1;
    private Employee manager2;
    private Employee emp1;
    private Employee emp2;
    private Employee emp3;
    private Map<String, Employee> employees;

    @BeforeEach
    void setUp() {
        
        ceo = new Employee("1", "Alice", "Smith", null, 300000);
        manager1 = new Employee("2", "Bob", "Johnson", "1", 150000);
        manager2 = new Employee("3", "Carol", "Williams", "1", 120000);
        emp1 = new Employee("4", "Dave", "Brown", "2", 80000);
        emp2 = new Employee("5", "Eve", "Davis", "2", 85000);
        emp3 = new Employee("6", "Frank", "Miller", "3", 70000);

        
        manager1.getSubordinates().add(emp1);
        manager1.getSubordinates().add(emp2);
        manager2.getSubordinates().add(emp3);
        ceo.getSubordinates().add(manager1);
        ceo.getSubordinates().add(manager2);

        employees = new HashMap<>();
        employees.put(ceo.getEmpId(), ceo);
        employees.put(manager1.getEmpId(), manager1);
        employees.put(manager2.getEmpId(), manager2);
        employees.put(emp1.getEmpId(), emp1);
        employees.put(emp2.getEmpId(), emp2);
        employees.put(emp3.getEmpId(), emp3);
    }


    @Test
    void testAnalyzeSalaries_ManagerUnderpaid() {

        manager1.setSalary(80000); 

        EmployeeAnalyzer analyzer = new EmployeeAnalyzer(employees);
        assertDoesNotThrow(analyzer::analyzeSalaries);
    }

    @Test
    void testAnalyzeSalaries_ManagerOverpaid() {
    
        manager2.setSalary(200000); 

        EmployeeAnalyzer analyzer = new EmployeeAnalyzer(employees);
        assertDoesNotThrow(analyzer::analyzeSalaries);
    }

    @Test
    void testAnalyzeReportingLines_DepthOK() {
        EmployeeAnalyzer analyzer = new EmployeeAnalyzer(employees);
        for (Employee e : employees.values()) {
            assertTrue(analyzer.calculateDepth(e) <= 2);
        }
    }

    @Test
    void testAnalyzeReportingLines_TooDeep() {
       
        Employee emp4 = new Employee("7", "Grace", "Wilson", "6", 60000);
        emp3.getSubordinates().add(emp4);
        employees.put(emp4.getEmpId(), emp4);

        EmployeeAnalyzer analyzer = new EmployeeAnalyzer(employees);
        int depth = analyzer.calculateDepth(emp4);

        Employee emp5 = new Employee("8", "Heidi", "Moore", "7", 55000);
        emp4.getSubordinates().add(emp5);
        employees.put(emp5.getEmpId(), emp5);

        depth = analyzer.calculateDepth(emp5);
        assertEquals(4, depth, "Heidi should have a depth of 4");
    }

    @Test
    void testCalculateDepth() {
        EmployeeAnalyzer analyzer = new EmployeeAnalyzer(employees);
        assertEquals(0, analyzer.calculateDepth(ceo));
        assertEquals(1, analyzer.calculateDepth(manager1));
        assertEquals(2, analyzer.calculateDepth(emp1));
    }
}
