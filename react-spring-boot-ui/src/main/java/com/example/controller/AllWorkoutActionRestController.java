package com.example.controller;

import java.lang.invoke.MethodType;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.controller.dao.WorkoutMicroserviceClient;
import com.example.entity.MyWorkoutRecords;
import com.example.entity.Reference;
import com.example.entity.WorkoutRecords2Send;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

//import com.cloudant.client.api.Database;
//import com.cloudant.client.api.model.IndexField;
//import com.cloudant.client.api.model.Response;
//import com.cloudant.client.api.model.IndexField.SortOrder;

@RestController
@RequestMapping(value={"/myworkout","/ibm/fitness-arena/myworkout"})
@RefreshScope
public class AllWorkoutActionRestController {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired WorkoutMicroserviceClient workoutClient;

	private static String iotId;
	private static String backupIotId;
	private static String startTime;
	
	@RequestMapping(method=RequestMethod.POST)
	public @ResponseBody ResponseEntity<String> saveWorkoutRecords(@RequestBody MyWorkoutRecords workout) {
		
//		workout.setTime(startTime);
//		startTime = null;

		
		System.out.println("My Workout Record : "+workout);
		
//		return restTemplate.postForObject("http://localhost:8081/myworkout", workout, String.class);
		
		return new ResponseEntity<String>(workoutClient.saveWorkoutRecords(workout), HttpStatus.CREATED);
	}
	
	
	@RequestMapping(value="/start", method=RequestMethod.POST)
	public @ResponseBody String startWorkout(@RequestBody WorkoutRecords2Send workout) {
		
//		System.out.println("My Workout Start Details : "+workout);
//		iotId = "1103";
//		if(iotId!=null) backupIotId=iotId;
//		System.out.println("\n \n IOT ID "+iotId+" received back at "+Calendar.getInstance().getTime());
////		iotId = restTemplate.postForObject("http://localhost:8081/myworkout", workout, String.class);
//		
////		System.out.println("We got a response from IOT Server with Id: "+iotId);
//		
////		return new ResponseEntity<String>(restTemplate.postForObject("http://localhost:8081/myworkout", workout, String.class), HttpStatus.CREATED);
//		return workout.toString();

		
		
//		startTime = workout.getTime();
		
		
		System.out.println("My Workout Start Details : "+workout);
		
		String something  = restTemplate.postForObject("https://abc-finance-demo.mybluemix.net/heartrate", workout, String.class);
		
		iotId = something.substring(15,something.length()-1);
		
		System.out.println("\n \n IOT ID "+iotId+" received back at "+Calendar.getInstance().getTime());
		
		if(iotId!=null) backupIotId=iotId;
		System.out.println("We got a response from IOT Server with Id: "+iotId);
		
//		return new ResponseEntity<String>(restTemplate.postForObject("http://localhost:8081/myworkout", workout, String.class), HttpStatus.CREATED);
		return workout.toString();
	}

	@RequestMapping(value="/stop",method=RequestMethod.GET, produces="application/json")
	public ResponseEntity<String> stopWorkout() {


////		ResponseEntity<List> workout = null;
////		workout = restTemplate.getForEntity("http://localhost:8081/myworkout/list?iotId="+iotId,List.class);
////		if(workout!=null) {
////			iotId = null;
////			new AllWorkoutActionRestController().saveWorkoutRecords((MyWorkoutRecords)workout.getBody().get(0));
////		}
////				
////		HttpHeaders header = new HttpHeaders();
////		 header.add("Access-Control-Allow-Origin", "*");
////		 header.add("memberId", memberId);
////		return new ResponseEntity<List>(workoutClient.getWorkoutRecordsList(memberId), header, HttpStatus.CREATED);
//		iotId = null;
//		System.out.println("\n \n IOT ID "+backupIotId+" resetted to "+iotId+" at time "+Calendar.getInstance().getTime());
//		System.out.println("Workout Stop Service has called !!!\n\n");
//		return null;
		
		Reference rev = new Reference(backupIotId);
		String backResponse = restTemplate.postForObject("https://abc-finance-demo.mybluemix.net/workout", rev, String.class);
		iotId = null;
		System.out.println("\n \n IOT ID "+backupIotId+" resetted to "+iotId+" at time "+Calendar.getInstance().getTime());
		System.out.println("Workout Stop Service has called !!!\n\n");
		return new ResponseEntity<String>(backResponse, HttpStatus.CREATED);
		
	}	
	
	@RequestMapping(method=RequestMethod.GET, produces="application/json")
	@HystrixCommand(fallbackMethod="getFallback",commandProperties={
			 @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value= "10000")
	 })	
	public ResponseEntity<List> getWorkoutRecords(@RequestParam(required=false) String memberId) {

		Map<String, String> params = new HashMap<String,String>();
		params.put("memberId", memberId);
//		ResponseEntity<List> list = null;
//		if(memberId!=null)  list = restTemplate.getForEntity("http://localhost:8081/myworkout/list?memberId="+memberId,List.class);
//		else list = restTemplate.getForEntity("http://localhost:8081/myworkout/list",List.class);
		
		HttpHeaders header = new HttpHeaders();
		 header.add("Access-Control-Allow-Origin", "*");
		 header.add("memberId", memberId);
		return new ResponseEntity<List>(workoutClient.getWorkoutRecordsList(memberId), header, HttpStatus.OK);
	}
	
	 //Hystrix Fallback method for getMemberRecords() method
	 public ResponseEntity<List> getFallback(@RequestParam(required=false) String memberId){
		List fallback = new ArrayList<>();
		MyWorkoutRecords workout = new MyWorkoutRecords();
		workout.setWorkoutId("fallback");
		workout.setWorkoutName("Some problem has occured at backend.... Please try again after some time");
		fallback.add(workout);
		return new ResponseEntity<List>(fallback,HttpStatus.TEMPORARY_REDIRECT);
	 }

}
