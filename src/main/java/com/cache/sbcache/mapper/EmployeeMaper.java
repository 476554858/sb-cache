package com.cache.sbcache.mapper;

import com.cache.sbcache.pojo.Employee;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface EmployeeMaper {
    @Select("select * from employee where id=#{id}")
    public Employee getEmpById(Integer id);

    @Update("UPDATE employee SET last_name=#{lastName},email=#{email},gender=#{gender},d_id=#{dId} WHERE id=#{id}")
    public void updateEmp(Employee employee);


    @Insert("INSERT INTO employee(last_name,email,gender,d_id) VALUES(#{lastName},#{email},#{gender},#{dId})")
    public void insertEmployee(Employee employee);

    @Select("SELECT * FROM employee WHERE last_name = #{lastName}")
    Employee getEmpByLastName(String lastName);
}
