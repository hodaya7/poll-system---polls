package com.pollsSystem.pollsSystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class PollsSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(PollsSystemApplication.class, args);
	}

}
