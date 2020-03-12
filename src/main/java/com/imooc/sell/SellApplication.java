package com.imooc.sell;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * SellApplication class
 *
 * @author hujun
 * @date 2020/02/05
 */
@MapperScan(basePackages = "com.imooc.sell.Data2Object.mapper")
@SpringBootApplication
@EnableCaching
public class SellApplication {

	public static void main(String[] args) {
		SpringApplication.run(SellApplication.class, args);
	}

}
