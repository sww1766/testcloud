package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(basePackages= {"com.service"})
public class ConsumerFeign {
	public static void main(String[] args) throws Exception {
		SpringApplication.run(ConsumerFeign.class, args);
	}
}
