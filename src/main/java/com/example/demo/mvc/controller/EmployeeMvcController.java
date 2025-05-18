package com.example.demo.mvc.controller;

import com.example.demo.employee_model.EmployeeModel;
import com.example.demo.employee_service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.Map;
import java.util.DoubleSummaryStatistics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/mvc/employees")
public class EmployeeMvcController {

    @Autowired
    private IEmployeeService employeeService;
    
    /**
     * Initialize the WebDataBinder to handle date conversion
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, 
                new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
        
        // Register a StringTrimmerEditor to handle empty strings
        // This will convert empty strings to null which helps with validation
        org.springframework.beans.propertyeditors.StringTrimmerEditor stringTrimmer = 
                new org.springframework.beans.propertyeditors.StringTrimmerEditor(true);
        binder.registerCustomEditor(String.class, stringTrimmer);
    }
    
    // List all employees
    @GetMapping
    public String getAllEmployees(Model model) {
        try {
            List<EmployeeModel> employees = employeeService.getEmployees();
            model.addAttribute("employees", employees);
            model.addAttribute("pageTitle", "Employee List");
            return "employees/list";
        } catch (Exception e) {
            model.addAttribute("error", "Failed to retrieve employees");
            return "error";
        }
    }
    
    // Show employee details
    @GetMapping("/{empId}")
    public String getEmployeeDetails(@PathVariable Integer empId, Model model) {
        try {
            EmployeeModel employee = employeeService.getEmployeeDetails(empId);
            if (employee != null) {
                model.addAttribute("employee", employee);
                model.addAttribute("pageTitle", "Employee Details");
                return "employees/details";
            } else {
                model.addAttribute("error", "Employee not found");
                return "error";
            }
        } catch (Exception e) {
            model.addAttribute("error", "Failed to retrieve employee details");
            return "error";
        }
    }
    
    // Show create employee form
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        // Pass an EmployeeModel without an ID for creation
        model.addAttribute("employee", new EmployeeModel()); // ID will be null
        model.addAttribute("pageTitle", "Create Employee");
        return "employees/form";
    }
    
    // Create new employee
    @PostMapping("/save")
    public String createEmployee(@ModelAttribute EmployeeModel employee, RedirectAttributes redirectAttributes) {
        try {
            // The employee object from the form will not have an empId.
            // The service layer (and repository) will generate and set the ID.
            EmployeeModel savedEmployee = employeeService.addEmployeeDetails(employee);
            if (savedEmployee != null) {
                redirectAttributes.addFlashAttribute("message", "Employee created successfully with ID: " + savedEmployee.getEmpId());
                return "redirect:/mvc/employees";
            } else {
                redirectAttributes.addFlashAttribute("error", "Failed to create employee");
                return "redirect:/mvc/employees/new";
            }
        } catch (Exception e) {
            // Catching a more general exception now as ID collision is handled by auto-generation
            redirectAttributes.addFlashAttribute("error", "Error creating employee: " + e.getMessage());
            return "redirect:/mvc/employees/new";
        }
    }    // Show edit employee form
    @GetMapping("/edit/{empId}")
    public String showEditForm(@PathVariable(required = false) Integer empId, Model model, RedirectAttributes redirectAttributes) {
        try {
            // Check if empId is null
            if (empId == null) {
                redirectAttributes.addFlashAttribute("error", "Employee ID is required");
                return "redirect:/mvc/employees";
            }
            
            System.out.println("Editing employee with ID: " + empId);
            EmployeeModel employee = employeeService.getEmployeeDetails(empId);
            if (employee != null) {
                System.out.println("Found employee: " + employee.getEmpName() + ", ID: " + employee.getEmpId());
                model.addAttribute("employee", employee);
                model.addAttribute("pageTitle", "Edit Employee");
                model.addAttribute("isEditMode", true);
                return "employees/form";
            } else {
                redirectAttributes.addFlashAttribute("error", "Employee not found");
                return "redirect:/mvc/employees";
            }
        } catch (NumberFormatException e) {
            redirectAttributes.addFlashAttribute("error", "Invalid employee ID format");
            return "redirect:/mvc/employees";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to retrieve employee: " + e.getMessage());
            return "redirect:/mvc/employees";
        }
    }    // Update employee
    @PostMapping("/update")
    public String updateEmployee(@ModelAttribute EmployeeModel employee, RedirectAttributes redirectAttributes) {
        try {
            // Log the received employee details
            System.out.println("Update request received. Employee ID: " + employee.getEmpId());
            System.out.println("Employee details: " + employee.getEmpName() + ", " + employee.getEmpMobile());
            
            // Safety check - if employee ID is null, we can't update
            if (employee.getEmpId() == null) {
                redirectAttributes.addFlashAttribute("error", "Employee ID is missing, cannot update");
                return "redirect:/mvc/employees";
            }
            
            // The employee object from the form will have an empId (from the hidden input).
            // The service layer will use this ID to find and update the employee
            EmployeeModel updatedEmployee = employeeService.updateEmployeeDetails(employee);
            if (updatedEmployee != null) {
                redirectAttributes.addFlashAttribute("message", "Employee updated successfully");
                return "redirect:/mvc/employees";
            } else {
                redirectAttributes.addFlashAttribute("error", "Failed to update employee - employee not found");
                return "redirect:/mvc/employees";
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to update employee: " + e.getMessage());
            // If we can get the employee ID, redirect to edit form, otherwise to list
            return (employee.getEmpId() != null) 
                   ? "redirect:/mvc/employees/edit/" + employee.getEmpId()
                   : "redirect:/mvc/employees";
        }
    }
      // Delete employee
    @GetMapping("/delete/{empId}")
    public String deleteEmployee(@PathVariable Integer empId, RedirectAttributes redirectAttributes) {
        try {
            System.out.println("Attempting to delete employee with ID: " + empId);
            
            // First verify the employee exists
            EmployeeModel employee = employeeService.getEmployeeDetails(empId);
            if (employee == null) {
                System.out.println("Employee with ID " + empId + " not found");
                redirectAttributes.addFlashAttribute("error", "Employee not found");
                return "redirect:/mvc/employees";
            }
            
            // If employee exists, proceed with deletion
            String result = employeeService.deleteEmployee(empId);
            System.out.println("Delete result: " + result);
            
            if (result != null) {
                redirectAttributes.addFlashAttribute("message", "Employee deleted successfully");
            } else {
                redirectAttributes.addFlashAttribute("error", "Failed to delete employee");
            }
            return "redirect:/mvc/employees";
        } catch (Exception e) {
            System.out.println("Exception while deleting employee: " + e.getMessage());
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Error deleting employee: " + e.getMessage());
            return "redirect:/mvc/employees";
        }
    }
    
    // Dashboard view
    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        try {
            List<EmployeeModel> employees = employeeService.getEmployees();
            
            // Create statistics object
            Map<String, Object> employeeStats = calculateEmployeeStatistics(employees);
            model.addAttribute("employeeStats", employeeStats);
            
            // Get recent employees (last 5)
            List<EmployeeModel> recentEmployees = employees.stream()
                .sorted((e1, e2) -> e2.getEmpId().compareTo(e1.getEmpId()))
                .limit(5)
                .collect(Collectors.toList());
            model.addAttribute("recentEmployees", recentEmployees);
            
            model.addAttribute("pageTitle", "Employee Dashboard");
            return "employees/dashboard";
        } catch (Exception e) {
            model.addAttribute("error", "Failed to load dashboard: " + e.getMessage());
            return "error";
        }
    }
    
    /**
     * Calculate various statistics for employees
     * @param employees List of all employees
     * @return Map containing calculated statistics
     */
    private Map<String, Object> calculateEmployeeStatistics(List<EmployeeModel> employees) {
        Map<String, Object> stats = new HashMap<>();
        
        // Exit early if there are no employees
        if (employees == null || employees.isEmpty()) {
            stats.put("totalCount", 0);
            stats.put("activeCount", 0);
            stats.put("averageSalary", 0.0);
            stats.put("maxSalary", 0.0);
            stats.put("minSalary", 0.0);
            stats.put("medianSalary", 0.0);
            stats.put("departmentCount", 0);
            stats.put("departmentDistribution", new ArrayList<>());
            stats.put("salaryRanges", new ArrayList<>());
            stats.put("positionDistribution", new ArrayList<>());
            return stats;
        }
        
        // Basic stats
        int totalCount = employees.size();
        long activeCount = employees.stream().filter(EmployeeModel::isActive).count();
        
        stats.put("totalCount", totalCount);
        stats.put("activeCount", activeCount);
        
        // Salary statistics
        DoubleSummaryStatistics salaryStats = employees.stream()
            .mapToDouble(EmployeeModel::getEmpSal)
            .summaryStatistics();
        
        double averageSalary = salaryStats.getAverage();
        double maxSalary = salaryStats.getMax();
        double minSalary = salaryStats.getMin();
        
        // Calculate median salary
        List<Double> sortedSalaries = employees.stream()
            .map(EmployeeModel::getEmpSal)
            .sorted()
            .collect(Collectors.toList());
        
        double medianSalary;
        int size = sortedSalaries.size();
        if (size % 2 == 0) {
            medianSalary = (sortedSalaries.get(size / 2 - 1) + sortedSalaries.get(size / 2)) / 2.0;
        } else {
            medianSalary = sortedSalaries.get(size / 2);
        }
        
        stats.put("averageSalary", averageSalary);
        stats.put("maxSalary", maxSalary);
        stats.put("minSalary", minSalary);
        stats.put("medianSalary", medianSalary);
        
        // Department distribution
        Map<String, Long> departmentCounts = employees.stream()
            .filter(e -> e.getDepartment() != null && !e.getDepartment().isEmpty())
            .collect(Collectors.groupingBy(
                EmployeeModel::getDepartment,
                Collectors.counting()
            ));
        
        // Count employees without department
        long noDepCount = employees.stream()
            .filter(e -> e.getDepartment() == null || e.getDepartment().isEmpty())
            .count();
        
        if (noDepCount > 0) {
            departmentCounts.put("Unassigned", noDepCount);
        }
        
        stats.put("departmentCount", departmentCounts.size());
        
        // Generate color for each department
        List<Map<String, Object>> departmentDistribution = new ArrayList<>();
        String[] colors = {"#4361ee", "#f72585", "#06d6a0", "#ffd166", "#7209b7", 
                        "#3a86ff", "#fb5607", "#8ac926", "#023e8a", "#9d4edd"};
        
        int i = 0;
        for (Map.Entry<String, Long> entry : departmentCounts.entrySet()) {
            Map<String, Object> deptData = new HashMap<>();
            deptData.put("name", entry.getKey());
            deptData.put("count", entry.getValue());
            deptData.put("color", colors[i % colors.length]);
            departmentDistribution.add(deptData);
            i++;
        }
        
        stats.put("departmentDistribution", departmentDistribution);
        
        // Salary range distribution
        // Determine salary ranges based on min and max
        double range = maxSalary - minSalary;
        int numRanges = 5; // Number of ranges to divide into
        double rangeSize = range / numRanges;
        
        List<Map<String, Object>> salaryRanges = new ArrayList<>();
        
        for (int j = 0; j < numRanges; j++) {
            double start = minSalary + (j * rangeSize);
            double end = j == numRanges - 1 ? maxSalary : minSalary + ((j + 1) * rangeSize);
            
            // Count employees in this range
            final double finalStart = start;
            final double finalEnd = end;
            
            long countInRange = employees.stream()
                .filter(e -> e.getEmpSal() >= finalStart && e.getEmpSal() <= finalEnd)
                .count();
            
            Map<String, Object> rangeData = new HashMap<>();
            rangeData.put("range", String.format("%.0f - %.0f", start, end));
            rangeData.put("count", countInRange);
            salaryRanges.add(rangeData);
        }
        
        stats.put("salaryRanges", salaryRanges);
        
        // Position distribution
        Map<String, Long> positionCounts = employees.stream()
            .filter(e -> e.getPosition() != null && !e.getPosition().isEmpty())
            .collect(Collectors.groupingBy(
                EmployeeModel::getPosition,
                Collectors.counting()
            ));
        
        // Count employees without position
        long noPositionCount = employees.stream()
            .filter(e -> e.getPosition() == null || e.getPosition().isEmpty())
            .count();
        
        if (noPositionCount > 0) {
            positionCounts.put("Unassigned", noPositionCount);
        }
        
        List<Map<String, Object>> positionDistribution = new ArrayList<>();
        
        for (Map.Entry<String, Long> entry : positionCounts.entrySet()) {
            Map<String, Object> posData = new HashMap<>();
            posData.put("name", entry.getKey());
            posData.put("count", entry.getValue());
            positionDistribution.add(posData);
        }
        
        stats.put("positionDistribution", positionDistribution);
        
        return stats;
    }
}
