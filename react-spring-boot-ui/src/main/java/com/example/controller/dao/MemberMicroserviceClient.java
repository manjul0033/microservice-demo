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

import com.example.entity.Member;

@FeignClient("MEMBERS-MICROSERVICE")
public interface MemberMicroserviceClient{
	
	@CrossOrigin
	@RequestMapping(value="/" ,method=RequestMethod.POST, consumes="application/json")
	public @ResponseBody String saveMemberRecords(@RequestBody(required=false) Member member);
	
	@CrossOrigin
	@RequestMapping("/initialize")
	public @ResponseBody List<Member> Initialize();
	
	@CrossOrigin
	@RequestMapping(value="/list" ,method=RequestMethod.GET, produces="application/json")
	public @ResponseBody List<Member> getMembersRecordsList(@RequestParam(name="memberId",required=false) String memberId);
	
	@CrossOrigin
	@RequestMapping(method=RequestMethod.GET, produces="application/json")
	public @ResponseBody ResponseEntity<List<Member>> getMembersRecords(@RequestParam(name="memberId",required=false) String memberId);

}
