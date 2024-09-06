package com.example.management;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync // 비동기 처리 활성화
@SpringBootApplication
@MapperScan("com.example.management")
public class ManagementApplication {

	public static void main(String[] args) {

		SpringApplication.run(ManagementApplication.class, args);
	}

}
