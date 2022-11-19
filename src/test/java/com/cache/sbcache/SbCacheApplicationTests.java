package com.cache.sbcache;

import com.cache.sbcache.pojo.Employee;
import com.cache.sbcache.mapper.EmployeeMaper;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.Redisson;
import org.redisson.RedissonRedLock;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.*;
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

	@Autowired
	Redisson redisson;

	@Autowired
	Redisson redisson2;

	@Autowired
	Redisson redisson3;


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


	@Test
	public void redissonLock(){
		String lockKey = "lockKey";
		RLock redissonLock = redisson.getLock(lockKey);
		redissonLock.lock(10, TimeUnit.SECONDS);
		System.out.println("业务处理.......");
		redissonLock.unlock();
		System.out.println("解锁");
	}

	@Test
	public void redLock(){
		String lockKey = "lockKey";
		RLock lock = redisson.getLock(lockKey);
		RLock lock2 = redisson2.getLock(lockKey);
		RLock lock3 = redisson3.getLock(lockKey);

		RedissonRedLock redLock = new RedissonRedLock(lock, lock2, lock3);
		redLock.lock(10, TimeUnit.SECONDS);
		System.out.println("业务处理.......");
		redLock.unlock();
		System.out.println("解锁");
	}

	/**
	 * redis事务测试
	 * 模拟下单扣减扣减金额操作
	 */
	@Test
	public void testMulti(){
		//商品库存
		final String storeKey = "goods_store_num";
		//用户账户余额
		final String balanceKey = "balance_key";

		redisTemplate.opsForValue().set(storeKey, 10);
		redisTemplate.opsForValue().set(balanceKey, 100);

		List result = (List) redisTemplate.execute(new SessionCallback() {
			@Override
			public Object execute(RedisOperations operations) throws DataAccessException {
				operations.watch(storeKey);

				Integer count  = (Integer) operations.opsForValue().get(storeKey);
				if (count <= 0) {
					return null;
				}
				operations.multi();
				operations.opsForValue().increment(storeKey, -1);
				operations.opsForValue().increment(balanceKey, -10);
				List result = operations.exec();
				return result;
			}
		});
		if(result != null && result.size() > 0){
			System.out.println("测试通过");
		}
	}

	/**
	 * 测试执行lua脚本
	 */
	@Test
	public void testLua(){
		//商品库存
		final String storeKey = "goods_store_num";
		//用户账户余额
		final String balanceKey = "balance_key";
		DefaultRedisScript<String> redisScript = new DefaultRedisScript<String>();
		redisScript.setResultType(String.class);
		redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("lua/test.lua")));
		Long res = (Long) redisTemplate.execute(redisScript, Arrays.asList(storeKey, balanceKey), 10);
		if(res == 1){
			System.out.println("执行成功");
		}
	}



}
