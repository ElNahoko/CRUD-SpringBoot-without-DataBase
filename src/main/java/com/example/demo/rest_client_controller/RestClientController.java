package com.example.demo.rest_client_controller;

import com.example.demo.employee_model.EmployeeModel;
import com.example.demo.rest_client_Service.IClientService;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/myApp")
public class RestClientController {

    @Autowired
    IClientService iClientService;

    @GetMapping("/getAll")
    public ResponseEntity<Object> getAllEmployees() throws UnirestException {
        ResponseEntity<Object> responseEntity = iClientService.getAll();
        return responseEntity;

    }

    @GetMapping("/get/{empId}")
    private ResponseEntity<Object> getEmployeeById(@PathVariable Integer empId) {
        ResponseEntity<Object> responseEntity = iClientService.getById(empId);
        return responseEntity;
    }

    @PostMapping("/new")
    public ResponseEntity<Object> addNewEmployee(@RequestBody EmployeeModel employeeModel) {
        ResponseEntity<Object> responseEntity = iClientService.addEmployee(employeeModel);
        return responseEntity;

    }

    @PutMapping("/update")
    public ResponseEntity<Object> updateEmployee(@RequestBody EmployeeModel employeeModel) {
        ResponseEntity<Object> responseEntity = iClientService.updateEmployee(employeeModel);
        return responseEntity;

    }

    @DeleteMapping("/delete/{empId}")
    public ResponseEntity<Object> deleteEmployeeById(@PathVariable Integer empId) {
        ResponseEntity<Object> responseEntity = iClientService.deleteById(empId);
        return responseEntity;
    }
}
