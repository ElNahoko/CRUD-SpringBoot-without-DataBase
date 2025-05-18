package com.example.demo.rest_client_Service;

import com.example.demo.exception_handling.AppResponse;
import com.example.demo.employee_model.EmployeeModel;
import com.example.demo.rest_client_controller.ClassAPI;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
public class ClientService implements IClientService {

    @Autowired
    private RestTemplate restTemplate;


    public ResponseEntity<Object> getAll(){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Object> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<Object> result = restTemplate.exchange(ClassAPI.GET_ALL_EMPLOYEES_API, HttpMethod.GET, entity, Object.class);
            AppResponse response = new AppResponse(HttpStatus.OK.value(), " TOTAL EMPLOYEES DETAILS ", result);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            AppResponse response = new AppResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), " NO DATA FOUND ", null);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    public ResponseEntity<Object> getById(Integer empId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Object> entity = new HttpEntity<>(headers);
        try {
            ResponseEntity<Object> result = restTemplate.exchange(ClassAPI.GET_EMPLOYEE_BY_ID + "/" + empId, HttpMethod.GET, entity, Object.class);
            AppResponse response = new AppResponse(HttpStatus.OK.value(), " EMPLOYEE DETAILS ", result);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            AppResponse response = new AppResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), " EMPLOYEE ID DOES NOT EXIST ", null);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

    }


    public ResponseEntity<Object> addEmployee(EmployeeModel employeeModel) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<EmployeeModel> entity = new HttpEntity<>(employeeModel, headers);
        try {
            ResponseEntity<Object> result = restTemplate.exchange(ClassAPI.NEW_EMPLOYEE, HttpMethod.POST, entity, Object.class);
            AppResponse response = new AppResponse(HttpStatus.OK.value(), " EMPLOYEE ADDED SUCCESSFULLY ", result);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            AppResponse response = new AppResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), " EMPLOYEE ID ALREADY EXIST ", null);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    public ResponseEntity<Object> updateEmployee(EmployeeModel employeeModel) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<EmployeeModel> entity = new HttpEntity<>(employeeModel, headers);
        try {
            ResponseEntity<Object> result = restTemplate.exchange(ClassAPI.UPDATE_EMPLOYEE, HttpMethod.PUT, entity, Object.class);
            AppResponse response = new AppResponse(HttpStatus.OK.value(), " EMPLOYEE UPDATED SUCCESSFULLY ", result);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            AppResponse response = new AppResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), " EMPLOYEE ID DOES NOT EXIST ", null);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

    }

    public ResponseEntity<Object> deleteById(Integer empId) {

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Object> entity = new HttpEntity<>(headers);
        try {
            ResponseEntity<Object> result = restTemplate.exchange(ClassAPI.DELETE_EMPLOYEE + "/" + empId, HttpMethod.DELETE, entity, Object.class);
            AppResponse response = new AppResponse(HttpStatus.OK.value(), " EMPLOYEE DELETED SUCCESSFULLY ", result);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            AppResponse response = new AppResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), " EMPLOYEE ID DOES NOT EXIST ", null);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

    }
}
