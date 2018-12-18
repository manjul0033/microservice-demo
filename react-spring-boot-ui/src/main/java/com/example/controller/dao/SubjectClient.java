package com.example.controller.dao;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("LAB-4-SUBJECT")
public interface SubjectClient{
	@RequestMapping(value="/", method=RequestMethod.GET)
	String getWord();
}
