package com.cache.sbcache;

import cn.hutool.core.io.IoUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.google.common.collect.Lists;
import org.mybatis.spring.annotation.MapperScan;
import org.omg.CORBA.TIMEOUT;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import java.io.*;
import java.nio.channels.FileChannel;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

@MapperScan("com.cache.sbcache.mapper")
@SpringBootApplication
@EnableCaching
@EnableRedisHttpSession
public class SbCacheApplication {

	public static void main(String[] args) {
		SpringApplication.run(SbCacheApplication.class, args);
	}

}
