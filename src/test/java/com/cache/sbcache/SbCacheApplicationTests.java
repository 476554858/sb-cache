package com.cache.sbcache;

import com.cache.sbcache.bean.Employee;
import com.cache.sbcache.mapper.EmployeeMaper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SbCacheApplicationTests {
	@Autowired
	EmployeeMaper employeeMaper;

	@Autowired
	StringRedisTemplate stringRedisTemplate;

	@Autowired
	RedisTemplate redisTemplate;

	@Autowired
	RedisTemplate<Object,Employee> employeeRedisTemplate;


	@Test
	public void test01(){
		//stringRedisTemplate.opsForValue().append("msg","hello");
		System.out.println(stringRedisTemplate.opsForValue().get("msg"));
	}

	//测试保存对象
	@Test
	public void test02(){
		redisTemplate.expire("s",1, TimeUnit.DAYS);
		Employee employee = employeeMaper.getEmpById(1);
		employeeRedisTemplate.opsForValue().set("emp-01",employee);
	}


	@Test
	public void contextLoads() {
		Employee employee = employeeMaper.getEmpById(1);
		System.out.println(employee.toString());
	}

	@Test
	public void createLock(){


	}

}
