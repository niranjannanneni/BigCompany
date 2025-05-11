package com.bigcompany;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class CSVReaderTest {

    @TempDir
    Path tempDir;

    @Test
    void testReadEmployees_SimpleHierarchy() throws IOException {
        // Prepare CSV content
        String csvContent = 
            "empId,firstName,lastName,salary,managerId\n" +
            "1,Alice,Smith,300000,\n" +
            "2,Bob,Johnson,150000,1\n" +
            "3,Carol,Williams,120000,1\n" +
            "4,Dave,Brown,80000,2\n" +
            "5,Eve,Davis,85000,2\n" +
            "6,Frank,Miller,70000,3\n";

        Path csvFile = tempDir.resolve("employees.csv");
        Files.write(csvFile, csvContent.getBytes());

        Map<String, Employee> employees = CSVReader.readEmployees(csvFile.toString());

        // Basic assertions
        assertEquals(6, employees.size());
        assertTrue(employees.containsKey("1"));
        assertTrue(employees.containsKey("6"));

        // CEO check
        Employee ceo = employees.get("1");
        assertEquals("Alice", ceo.getFirstName());
        assertEquals(2, ceo.getSubordinates().size());

        // Manager check
        Employee manager = employees.get("2");
        assertEquals("Bob", manager.getFirstName());
        assertEquals(2, manager.getSubordinates().size());

        // Subordinate check
        Employee emp = employees.get("4");
        assertEquals("Dave", emp.getFirstName());
        assertEquals(0, emp.getSubordinates().size());
    }

    @Test
    void testReadEmployees_HandlesMissingManagerId() throws IOException {
        String csvContent =
            "empId,firstName,lastName,salary,managerId\n" +
            "1,Alice,Smith,300000,\n" +
            "2,Bob,Johnson,150000,\n" +
            "3,Carol,Williams,120000,2\n";

        Path csvFile = tempDir.resolve("employees_missing_manager.csv");
        Files.write(csvFile, csvContent.getBytes());

        Map<String, Employee> employees = CSVReader.readEmployees(csvFile.toString());

        assertEquals(3, employees.size());
        assertNull(employees.get("1").getManagerId());
        assertNull(employees.get("2").getManagerId());
        assertEquals("2", employees.get("3").getManagerId());
    }

    @Test
    void testReadEmployees_HandlesNoSubordinates() throws IOException {
        String csvContent =
            "empId,firstName,lastName,salary,managerId\n" +
            "1,Alice,Smith,300000,\n";

        Path csvFile = tempDir.resolve("employees_single.csv");
        Files.write(csvFile, csvContent.getBytes());

        Map<String, Employee> employees = CSVReader.readEmployees(csvFile.toString());

        assertEquals(1, employees.size());
        Employee ceo = employees.get("1");
        assertEquals("Alice", ceo.getFirstName());
        assertEquals(0, ceo.getSubordinates().size());
    }

    @Test
    void testReadEmployees_InvalidFile_ThrowsException() {
        assertThrows(IOException.class, () -> {
            CSVReader.readEmployees("nonexistent_file.csv");
        });
    }
}
