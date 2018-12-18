package com.example.controller.dao;

import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient("FITNESSARENA-MICROSERVICE")
public interface FitnessArenaClient {

}
