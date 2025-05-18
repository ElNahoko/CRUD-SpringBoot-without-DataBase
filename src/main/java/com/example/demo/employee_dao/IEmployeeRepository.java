package com.example.demo.employee_dao;

import com.example.demo.employee_model.EmployeeModel;

import java.util.List;

public interface IEmployeeRepository {

    List<EmployeeModel> getAllEmployees();

    EmployeeModel getEmployeeDetails(Integer empId);

    EmployeeModel addEmployee(EmployeeModel employee);

    EmployeeModel updateEmployee(EmployeeModel employee);

    String deleteEmployeeDetails(Integer empId);

}
