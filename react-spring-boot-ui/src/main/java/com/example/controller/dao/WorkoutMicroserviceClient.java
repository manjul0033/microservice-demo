package com.example.controller.dao;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.entity.MyWorkoutRecords;

@FeignClient("WORKOUT-MICROSERVICE")
public interface WorkoutMicroserviceClient {

	@CrossOrigin
	@RequestMapping(value="/" ,method=RequestMethod.POST, consumes="application/json")
	public @ResponseBody String saveWorkoutRecords(@RequestBody MyWorkoutRecords workout);

	@CrossOrigin
	@RequestMapping("/initialize")
	public @ResponseBody List<MyWorkoutRecords> Initialize();

	@CrossOrigin
	@RequestMapping(value="/list" ,method=RequestMethod.GET, produces="application/json")
	public @ResponseBody List<MyWorkoutRecords> getWorkoutRecordsList(@RequestParam(name="memberId",required=false) String memberId);

	@CrossOrigin
	@RequestMapping(value="/" ,method=RequestMethod.GET, produces="application/json")
	public @ResponseBody ResponseEntity<List<MyWorkoutRecords>> getWorkoutRecords(@RequestParam(name="memberId",required=false) String memberId);

}
