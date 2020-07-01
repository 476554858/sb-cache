package com.cache.sbcache;

import com.cache.sbcache.bean.Employee;
import com.cache.sbcache.mapper.EmployeeMaper;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
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


	//布隆过滤器
	@Test
	public void bloomFilter(){
		int size = 1000000;

		BloomFilter<Integer> bloomFilter = BloomFilter.create(Funnels.integerFunnel(), size, 0.001);

		for (int i =  1; i <= size; i++){
			bloomFilter.put(i);
		}

		List<Integer> list = new ArrayList<Integer>(10000);
		for(int i = size + 10000; i < size + 20000; i++ ){
			if(bloomFilter.mightContain(i)){
				list.add(i);
			}
		}
		System.out.println("误判的数量:" + list.size());
	}

}
