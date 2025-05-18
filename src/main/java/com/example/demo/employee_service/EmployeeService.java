package com.example.demo.employee_service;

import com.example.demo.employee_model.EmployeeModel;
import com.example.demo.employee_dao.IEmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService implements IEmployeeService {

    @Autowired
    IEmployeeRepository iEmployeeRepository;

    @Override
    public List<EmployeeModel> getEmployees() {
        return iEmployeeRepository.getAllEmployees();
    }

    @Override
    public EmployeeModel getEmployeeDetails(Integer empId) {
        return iEmployeeRepository.getEmployeeDetails(empId);
    }

    @Override
    public EmployeeModel addEmployeeDetails(EmployeeModel employee) {
        return iEmployeeRepository.addEmployee(employee);
    }

    @Override
    public EmployeeModel updateEmployeeDetails(EmployeeModel employee) {
        return iEmployeeRepository.updateEmployee(employee);
    }

    @Override
    public String deleteEmployee(Integer empId) {
        return iEmployeeRepository.deleteEmployeeDetails(empId);
    }
}
