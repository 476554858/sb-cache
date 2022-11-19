package com.cache.sbcache.service;

import com.cache.sbcache.pojo.Employee;
import com.cache.sbcache.mapper.EmployeeMaper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

@CacheConfig(cacheNames = "emp",cacheManager = "employeeCacheManager")
@Service
public class EmployeeService {

    @Autowired
    EmployeeMaper employeeMaper;

    @Cacheable(cacheNames = {"emp"}/*,condition = "#a0>1",unless = "#a0==2"*/)
    public Employee getEmp(Integer id){
        System.out.println("员工查询"+id);
        return employeeMaper.getEmpById(id);
    }

    @CachePut(value = "emp",key="#employee.id")
    public Employee updateEmp(Employee employee){
        System.out.println("updateEmp:"+employee);
        employeeMaper.updateEmp(employee);
        return employee;
    }

    @CacheEvict(value = "emp",beforeInvocation = false,key = "#id")
    public void deleteEmp(Integer id){
        System.out.println("deleteEmp"+id);
    }

    //@Caching定义复杂的换成规则
    @Caching(
            cacheable = {
                    @Cacheable(value = "emp",key = "#lastName")
            },
            put = {
                    @CachePut(value = "emp",key = "#result.id"),
                    @CachePut(value = "emp",key = "#result.email")
            }
    )
    public Employee getEmpByLastName(String lastName){
        return employeeMaper.getEmpByLastName(lastName);
    }


}
