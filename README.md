Employee Analyzer
A Java application for analyzing employee hierarchies and salaries from CSV files.
This project reads employee data, builds reporting structures, and provides insightful analyses such as salary fairness and reporting line depth.

Features
CSV Import: Reads employee data from a CSV file.

Hierarchy Building: Automatically constructs reporting lines and subordinates.

Salary Analysis: Checks if managers are paid fairly compared to their teams.

Reporting Line Analysis: Detects employees with excessively long chains of command.

Extensible: Easily add more analysis features.

Project Structure
text
src/
  main/
    java/
      com/bigcompany/
        Employee.java
        EmployeeAnalyzer.java
        CSVReader.java
  test/
    java/
      com/bigcompany/
        EmployeeAnalyzerTest.java
        CSVReaderTest.java
CSV Format
The CSV should have the following columns (header required):

text
empId,firstName,lastName,salary,managerId
1,Alice,Smith,300000,
2,Bob,Johnson,150000,1
3,Carol,Williams,120000,1
4,Dave,Brown,80000,2
5,Eve,Davis,85000,2
6,Frank,Miller,70000,3
managerId is empty for the CEO/top-level employee.
