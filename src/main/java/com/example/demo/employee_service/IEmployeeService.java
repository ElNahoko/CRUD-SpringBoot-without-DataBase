package com.example.demo.employee_service;

import com.example.demo.employee_model.EmployeeModel;

import java.util.List;

public interface IEmployeeService {

    List<EmployeeModel> getEmployees();

    EmployeeModel getEmployeeDetails(Integer empId);

    EmployeeModel addEmployeeDetails(EmployeeModel employee);

    EmployeeModel updateEmployeeDetails(EmployeeModel employee);

    String deleteEmployee(Integer empId);

}
