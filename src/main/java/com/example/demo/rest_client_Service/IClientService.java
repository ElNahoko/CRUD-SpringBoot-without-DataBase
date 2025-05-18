package com.example.demo.rest_client_Service;

import com.example.demo.employee_model.EmployeeModel;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.http.ResponseEntity;

public interface IClientService {

    ResponseEntity<Object> getAll() throws UnirestException;

    ResponseEntity<Object> getById(Integer empId);

    ResponseEntity<Object> addEmployee(EmployeeModel employeeModel);

    ResponseEntity<Object> updateEmployee(EmployeeModel employeeModel);

    ResponseEntity<Object> deleteById(Integer empId);

}
