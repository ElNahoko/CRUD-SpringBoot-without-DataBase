package com.example.demo.employee_dao;

import com.example.demo.employee_model.EmployeeModel;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class EmployeeRepository implements IEmployeeRepository {
    private static final Map<Integer, EmployeeModel> employees = new HashMap<>();
    private static final AtomicInteger idCounter = new AtomicInteger(0);    // Static initializer block to populate initial data
    static {
        // Initialize with sample data for Moroccan employees, letting the repository assign IDs
        // IT Department employees
        addInitialEmployee("Ahmed Benali", "0661234567", 12500.00, 
            "ahmed.benali@company.ma", "IT", "Lead Developer", 
            LocalDate.of(2020, 3, 15), "Résidence Al Fath, Imm 5, Appt 12, Hay Riad, Rabat", true);

        addInitialEmployee("Fatima Zahra El Amrani", "0712345678", 9800.00, 
            "f.elamrani@company.ma", "IT", "QA Engineer", 
            LocalDate.of(2021, 7, 10), "17 Rue Ibn Sina, Quartier des Hôpitaux, Casablanca", true);
        
        // HR Department employees
        addInitialEmployee("Mohammed El Ouazzani", "0661234789", 11000.00, 
            "m.elouazzani@company.ma", "HR", "HR Manager", 
            LocalDate.of(2019, 11, 5), "Avenue Mohammed VI, Résidence Safae, Marrakech", true);
        
        // Finance Department employees
        addInitialEmployee("Nadia Tazi", "0761234567", 13500.00, 
            "n.tazi@company.ma", "Finance", "Financial Controller", 
            LocalDate.of(2022, 1, 20), "25 Rue Al Jazira, Quartier Gauthier, Casablanca", true);
        
        // Marketing Department employee
        addInitialEmployee("Karim Idrissi", "0631234567", 8700.00, 
            "k.idrissi@company.ma", "Marketing", "Marketing Specialist", 
            LocalDate.of(2022, 5, 12), "Résidence Nakhil, Appt 7, Avenue de Fès, Meknès", false);

        // Sales Department employee
        addInitialEmployee("Samira Benjelloun", "0612987654", 10300.00, 
            "s.benjelloun@company.ma", "Sales", "Sales Manager", 
            LocalDate.of(2021, 3, 25), "10 Rue Atlas, Agdal, Rabat", true);

        // Operations Department employee
        addInitialEmployee("Younes Benjilali", "0770123456", 9200.00, 
            "y.benjilali@company.ma", "Operations", "Operations Supervisor", 
            LocalDate.of(2023, 2, 10), "8 Rue Bani Marine, Ville Nouvelle, Fès", true);
    }

    // Helper method for static initializer to add employees and assign IDs
    private static void addInitialEmployee(String name, String mobile, double salary,
                                         String email, String department, String position,
                                         LocalDate hireDate, String address, boolean active) {
        EmployeeModel employee = new EmployeeModel(name, mobile, salary,
                email, department, position, hireDate, address, active);
        employee._internalSetEmpId(idCounter.incrementAndGet());
        employees.put(employee.getEmpId(), employee);
    }

    @Override
    public List<EmployeeModel> getAllEmployees() {
        return new ArrayList<>(employees.values());
    }

    @Override
    public EmployeeModel getEmployeeDetails(Integer empId) {
        return employees.get(empId);
    }

    @Override
    public EmployeeModel addEmployee(EmployeeModel employee) {
        // Generate new ID for the employee
        employee._internalSetEmpId(idCounter.incrementAndGet());
        employees.put(employee.getEmpId(), employee);
        return employee;
    }

    @Override
    public EmployeeModel updateEmployee(EmployeeModel employee) {
        // Ensure the employee exists before updating
        // The ID of the employee should not be changed during an update
        if (employees.containsKey(employee.getEmpId())) {
            // Retrieve the existing employee to preserve its ID
            EmployeeModel existingEmployee = employees.get(employee.getEmpId());
            
            // Update mutable fields
            existingEmployee.setEmpName(employee.getEmpName());
            existingEmployee.setEmpMobile(employee.getEmpMobile());
            existingEmployee.setEmpSal(employee.getEmpSal());
            existingEmployee.setEmail(employee.getEmail());
            existingEmployee.setDepartment(employee.getDepartment());
            existingEmployee.setPosition(employee.getPosition());
            existingEmployee.setHireDate(employee.getHireDate());
            existingEmployee.setAddress(employee.getAddress());
            existingEmployee.setActive(employee.isActive());
            
            employees.put(existingEmployee.getEmpId(), existingEmployee);
            return existingEmployee;
        } else {
            return null; // Or throw an exception if employee not found
        }
    }    @Override
    public String deleteEmployeeDetails(Integer empId) {
        System.out.println("Repository: Attempting to delete employee with ID: " + empId);
        
        // Check if employee exists
        if (employees.containsKey(empId)) {
            // Remove the employee and return success
            EmployeeModel removedEmployee = employees.remove(empId);
            System.out.println("Repository: Successfully removed employee: " + 
                              (removedEmployee != null ? removedEmployee.getEmpName() : "unknown"));
            return "DELETED SUCCESSFULLY";
        } else {
            System.out.println("Repository: Employee with ID " + empId + " not found for deletion");
            // Return an error message instead of null to provide more context
            return "EMPLOYEE NOT FOUND";
        }
    }
}
