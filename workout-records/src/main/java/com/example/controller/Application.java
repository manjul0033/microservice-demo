package com.example.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import javax.annotation.Resource;

import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.ClientBuilder;
import com.cloudant.client.api.Database;
import java.net.URL;


@SpringBootApplication
@EnableDiscoveryClient
@EnableAutoConfiguration
@RefreshScope
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        System.out.println("Workout Data Spring Boot Microservice is ready");
    }

    @Resource
	private CloudantConfig dbconfig;

    @Bean
    @RefreshScope
    public Database cloudantclient () {

      Database db = null;
      try {

        CloudantClient client = ClientBuilder.url(new URL(dbconfig.getHost()))
                    .username(dbconfig.getUsername())
                    .password(dbconfig.getPassword())
                    .build();

          //Get socialReview db
          // Get a Database instance to interact with, but don't create it if it doesn't already exist
          db = client.database("workoutrecordsdb", true);
          System.out.println(db.info().toString());

      }catch (Exception e)
      {
        e.printStackTrace();
      }

    return db;
  }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD")
                        .allowedHeaders("header1", "header2") //What is this for?
                        .allowCredentials(true);
            }
        };
    }
    
}
