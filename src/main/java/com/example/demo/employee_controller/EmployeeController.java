package com.example.demo.employee_controller;

import com.example.demo.exception_handling.AppResponse;
import com.example.demo.employee_model.EmployeeModel;
import com.example.demo.employee_service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmployeeController {

    @Autowired
    IEmployeeService iEmployeeService;

    @GetMapping("/employees")
    public ResponseEntity<Object> getEmployees() {
        try {
            List<EmployeeModel> employeeModels = iEmployeeService.getEmployees();
            if (!employeeModels.isEmpty()) {
                AppResponse response = new AppResponse(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), employeeModels);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            AppResponse response = new AppResponse(HttpStatus.BAD_REQUEST.value(), "NO DATA FUND ", "THERE IS NO DATA");
            ResponseEntity<Object> responseEntity = new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            return responseEntity;
        }
    }

    @GetMapping("/getEmployee/{empId}")
    public ResponseEntity<Object> getEmployeeDetails(@PathVariable Integer empId) {
        try {
            EmployeeModel employeeModel = iEmployeeService.getEmployeeDetails(empId);
            if (employeeModel != null) {
                AppResponse response = new AppResponse(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), employeeModel);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                throw new Exception();
            }
        } catch (Exception exception) {
            AppResponse response = new AppResponse(HttpStatus.BAD_REQUEST.value(), "EMPLOYEE ID NOT EXISTS, PLEASE CHECK IT", "EMPLOYEE ID = " + empId);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/newEmployee")
    public ResponseEntity<Object> addEmployeeDetails(@RequestBody EmployeeModel employee) {
        try {
            EmployeeModel employeeModel = iEmployeeService.addEmployeeDetails(employee);
            if (employeeModel != null) {
                AppResponse response = new AppResponse(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), employeeModel);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                throw new Exception();
            }
        } catch (Exception exception) {
            AppResponse response = new AppResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "EMPLOYEE ID EXISTS, PLEASE INSERT DATA IN ANOTHER EMPLOYEE ID", "EMPLOYEE ID = " + employee.getEmpId());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/updateEmployee")
    public ResponseEntity<Object> updateEmployeeDetails(@RequestBody EmployeeModel employee) {
        try {
            EmployeeModel employeeModel = iEmployeeService.updateEmployeeDetails(employee);
            if (employeeModel != null) {
                AppResponse response = new AppResponse(HttpStatus.OK.value(), " RECORD UPDATED  SUCCESSFULLY.. ", employeeModel);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                throw new Exception();
            }
        } catch (Exception exception) {
            AppResponse response = new AppResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), " EMPLOYEE ID NOT EXISTS", "INSERT DATA WHERE ID = " + employee.getEmpId());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteEmployee/{empId}")
    public ResponseEntity<Object> deleteEmployee(@PathVariable Integer empId) {
        try {
            String message = iEmployeeService.deleteEmployee(empId);
            if (message != null) {
                AppResponse response = new AppResponse(HttpStatus.OK.value(), "RECORD DELETED SUCCESSFULLY..", message);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                throw new Exception();
            }
        } catch (
                Exception exception) {
            AppResponse response = new AppResponse(HttpStatus.BAD_REQUEST.value(), "INVALID EMPLOYEE ID", "EMPLOYEE ID = " + empId);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
