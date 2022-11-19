package com.cache.sbcache.controller;

import com.cache.sbcache.pojo.Employee;
import com.cache.sbcache.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @GetMapping("/emp/{id}")
    public Employee getEmployee(@PathVariable Integer id){

        Employee employee = employeeService.getEmp(id);
        return employee;
    }

    @GetMapping("/emp")
    public Employee getEmployee(Employee employee){
        Employee e = employeeService.updateEmp(employee);
        return e;
    }

    @GetMapping("/delemp")
    public String delEmployee(Integer id){
        employeeService.deleteEmp(id);
        return "success";
    }

    @GetMapping("/emp/lastName/{lastName}")
    public Employee getEmpByLastName(@PathVariable String lastName){
        return employeeService.getEmpByLastName(lastName);
    }





















}
