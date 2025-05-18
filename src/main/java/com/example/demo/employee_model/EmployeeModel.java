package com.example.demo.employee_model;


import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
public class EmployeeModel {
    // Changed from @Setter(AccessLevel.NONE) to allow binding during form submission for updates
    private Integer empId;
    private String empName;
    private String empMobile;
    private double empSal;
    
    // Additional properties
    private String email;
    private String department;
    private String position;
    private LocalDate hireDate;
    private String address;
    private boolean active = true; // Default is active

    // Constructor with essential fields
    public EmployeeModel(String empName, String empMobile, double empSal) {
        this.empName = empName;
        this.empMobile = empMobile;
        this.empSal = empSal;
    }

    // Constructor with all fields except ID
    public EmployeeModel(String empName, String empMobile, double empSal, 
                        String email, String department, String position, 
                        LocalDate hireDate, String address, boolean active)
    {
        this.empName = empName;
        this.empMobile = empMobile;
        this.empSal = empSal;
        this.email = email;
        this.department = department;
        this.position = position;
        this.hireDate = hireDate;
        this.address = address;
        this.active = active;
    }
    
    // This internal setter method can be used by the repository
    // but we'll also use the regular setter for form binding
    public void _internalSetEmpId(Integer empId) {
        this.empId = empId;
    }
    
    // Custom setter to add logging/debugging (not actually overriding anything)
    public void setEmpId(Integer empId) {
        System.out.println("Setting employee ID to: " + empId);
        this.empId = empId;
    }
}
