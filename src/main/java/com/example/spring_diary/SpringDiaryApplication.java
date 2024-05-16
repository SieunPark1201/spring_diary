package com.example.spring_diary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.example.spring_diary")

public class SpringDiaryApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringDiaryApplication.class, args);
	}

}
