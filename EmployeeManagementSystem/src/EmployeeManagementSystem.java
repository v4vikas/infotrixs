import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EmployeeManagementSystem {

    private static final String FILE_PATH = "C:\\Users\\v4vik\\OneDrive\\Desktop\\EmployeeManagementSystem\\src\\employees.txt";
    private static final String DELIMITER = ",";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Employee> employees = new ArrayList<>();

        loadEmployeesFromFile(employees);

        while (true) {
            System.out.println("Employee Management System");
            System.out.println("1. Add Employee");
            System.out.println("2. View All Employees");
            System.out.println("3. Update Employee");
            System.out.println("4. Delete Employee");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    addEmployee(scanner, employees);
                    break;
                case 2:
                    viewAllEmployees(employees);
                    break;
                case 3:
                    updateEmployee(scanner, employees);
                    break;
                case 4:
                    deleteEmployee(scanner, employees);
                    break;
                case 5:
                    saveEmployeesToFile(employees);
                    System.out.println("Exiting the program...");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void loadEmployeesFromFile(List<Employee> employees) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(DELIMITER);
                String name = data[0];
                int age = Integer.parseInt(data[1]);
                String designation = data[2];
                double salary = Double.parseDouble(data[3]);
                employees.add(new Employee(name, age, designation, salary));
            }
        } catch (IOException e) {
            System.out.println("Error loading employees from file: " + e.getMessage());
        }
    }

    private static void saveEmployeesToFile(List<Employee> employees) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Employee employee : employees) {
                String line = employee.getName() + DELIMITER + employee.getAge() + DELIMITER + employee.getDesignation()
                        + DELIMITER + employee.getSalary();
                writer.write(line);
                writer.newLine();
            }
            System.out.println("Employees saved to file.");
        } catch (IOException e) {
            System.out.println("Error saving employees to file: " + e.getMessage());
        }
    }

    private static void addEmployee(Scanner scanner, List<Employee> employees) {
        System.out.print("Enter the employee name: ");
        String name = scanner.nextLine();
        System.out.print("Enter the employee age: ");
        int age = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character
        System.out.print("Enter the employee designation: ");
        String designation = scanner.nextLine();
        System.out.print("Enter the employee salary: ");
        double salary = scanner.nextDouble();
        scanner.nextLine(); // Consume the newline character

        Employee employee = new Employee(name, age, designation, salary);
        employees.add(employee);

        System.out.println("Employee added successfully.");
    }

    private static void viewAllEmployees(List<Employee> employees) {
        if (employees.isEmpty()) {
            System.out.println("No employees found.");
        } else {
            System.out.println("Employee List:");
            for (Employee employee : employees) {
                System.out.println(employee);
            }
        }
    }

    private static void updateEmployee(Scanner scanner, List<Employee> employees) {
        System.out.print("Enter the employee name to update: ");
        String name = scanner.nextLine();

        Employee employee = findEmployeeByName(name, employees);
        if (employee == null) {
            System.out.println("Employee not found.");
            return;
        }

        System.out.println("Enter new details (press Enter to keep the current value):");

        System.out.print("Enter the new employee name: ");
        String newName = scanner.nextLine();
        if (!newName.isEmpty()) {
            employee.setName(newName);
        }

        System.out.print("Enter the new employee age: ");
        String newAgeStr = scanner.nextLine();
        if (!newAgeStr.isEmpty()) {
            try {
                int newAge = Integer.parseInt(newAgeStr);
                employee.setAge(newAge);
            } catch (NumberFormatException e) {
                System.out.println("Invalid age format. Age not updated.");
            }
        }

        System.out.print("Enter the new employee designation: ");
        String newDesignation = scanner.nextLine();
        if (!newDesignation.isEmpty()) {
            employee.setDesignation(newDesignation);
        }

        System.out.print("Enter the new employee salary: ");
        String newSalaryStr = scanner.nextLine();
        if (!newSalaryStr.isEmpty()) {
            try {
                double newSalary = Double.parseDouble(newSalaryStr);
                employee.setSalary(newSalary);
            } catch (NumberFormatException e) {
                System.out.println("Invalid salary format. Salary not updated.");
            }
        }

        System.out.println("Employee details updated successfully.");
    }

    private static void deleteEmployee(Scanner scanner, List<Employee> employees) {
        System.out.print("Enter the employee name to delete: ");
        String name = scanner.nextLine();

        Employee employee = findEmployeeByName(name, employees);
        if (employee == null) {
            System.out.println("Employee not found.");
        } else {
            employees.remove(employee);
            System.out.println("Employee deleted successfully.");
        }
    }

    private static Employee findEmployeeByName(String name, List<Employee> employees) {
        for (Employee employee : employees) {
            if (employee.getName().equalsIgnoreCase(name)) {
                return employee;
            }
        }
        return null;
    }

    private static class Employee {
        private String name;
        private int age;
        private String designation;
        private double salary;

        public Employee(String name, int age, String designation, double salary) {
            this.name = name;
            this.age = age;
            this.designation = designation;
            this.salary = salary;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getDesignation() {
            return designation;
        }

        public void setDesignation(String designation) {
            this.designation = designation;
        }

        public double getSalary() {
            return salary;
        }

        public void setSalary(double salary) {
            this.salary = salary;
        }

        @Override
        public String toString() {
            return "Name: " + name + ", Age: " + age + ", Designation: " + designation + ", Salary: " + salary;
        }
    }
}
