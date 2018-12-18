package com.example.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableHystrix
@EnableAutoConfiguration
public class ReactSpringBootUiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReactSpringBootUiApplication.class, args);
		System.out.println("React js Fitness Arena UI microservice is ready");
	}
	
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
