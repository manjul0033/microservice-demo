package com.example.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.controller.dao.MemberMicroserviceClient;
import com.example.entity.Member;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;


@RestController
@RequestMapping(value={"/members","/ibm/fitness-arena/members"})
@RefreshScope
public class AllMemberActionRestController {

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired MemberMicroserviceClient memberClient;
	
	@RequestMapping(method=RequestMethod.POST)
	public @ResponseBody ResponseEntity<String> saveMemberRecords(@RequestBody Member member) throws InterruptedException {
		
		System.out.println("Member Record : "+member);
		String response = memberClient.saveMemberRecords(member);
		Thread.sleep(5000);
//		return restTemplate.postForObject("http://localhost:8082/members", member, String.class);		
		return new ResponseEntity<String>(response,HttpStatus.CREATED);
	}
	
	
	@RequestMapping(method=RequestMethod.GET, produces="application/json")
	@HystrixCommand(fallbackMethod="getFallback",commandProperties={
			 @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value= "10000")
	 })
	public ResponseEntity<List> getMemberRecords(@RequestParam(required=false) String memberId) {
		
		ResponseEntity<List> list = null;
//		if(memberId!=null)  list = restTemplate.getForEntity("http://localhost:8082/members/list?memberId="+memberId,List.class);
//		else list = restTemplate.getForEntity("http://localhost:8082/members/list",List.class);		
		HttpHeaders header = new HttpHeaders();
		 header.add("Access-Control-Allow-Origin", "*");
		 header.add("memberId", memberId);

		 return new ResponseEntity<List>(memberClient.getMembersRecordsList(memberId), header, HttpStatus.OK); 
	}
	
	 //Hystrix Fallback method for getMemberRecords() method
	 public ResponseEntity<List> getFallback(@RequestParam(required=false) String memberId){
		List fallback = new ArrayList<>();
		Member member = new Member();
		member.setMemberId("fallback");
		member.setFirstName("Some problem has occured at backend.... Please try again after some time");
		fallback.add(member);
		HttpHeaders header = new HttpHeaders();
		 header.add("Access-Control-Allow-Origin", "*");
		 header.add("memberId", memberId);
		System.out.println("FallBack has called !!!");
		return new ResponseEntity<List>(fallback,header, HttpStatus.TEMPORARY_REDIRECT);
	 }
	
}
