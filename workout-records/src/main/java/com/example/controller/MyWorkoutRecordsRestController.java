package com.example.controller;

import java.lang.invoke.MethodType;
import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cloudant.client.api.Database;
import com.cloudant.client.api.model.IndexField;
import com.cloudant.client.api.model.Response;
import com.example.entity.MyWorkoutRecords;
import com.cloudant.client.api.model.IndexField.SortOrder;

@RestController
@RefreshScope
public class MyWorkoutRecordsRestController {
	
	@Autowired
	private Database db;
	
	@RequestMapping(method=RequestMethod.POST, consumes="application/json")
	@CrossOrigin
	public @ResponseBody String saveWorkoutRecords(@RequestBody MyWorkoutRecords workout) {
		
		String workoutId = new ManageWorkoutIdController().getWorkoutId();
		workout.setWorkoutId(workoutId);
		System.out.println("My Workout Record : "+workout);
		
		Response r = null;
		if(workout!=null) {
			r =db.post(workout);
		}
		
		workoutId = Integer.toString((Integer.parseInt(workoutId)+1));
		System.out.println("updated memberId in table : "+(new ManageWorkoutIdController()).updateWorkoutId(workoutId) );
		
		return r.getId();
//		return workout.toString();
	}
		
	@RequestMapping("/initialize")
	@CrossOrigin
	public @ResponseBody List<MyWorkoutRecords> Initialize() {
//		db.remove(new MyWorkoutRecords(true));
		System.out.println("Initializing the db :" +db.post(new MyWorkoutRecords(true)));
		return db.findByIndex("{\"memberId\" : " + new MyWorkoutRecords(true).getMemberId() + "}", MyWorkoutRecords.class);
	}
	
	@RequestMapping(value="/list" ,method=RequestMethod.GET, produces="application/json")
	public @ResponseBody List<MyWorkoutRecords> getWorkoutRecordsList(@RequestParam(required=false) String memberId) {
		System.out.println("MemberId getting :"+memberId);
		List<MyWorkoutRecords> allRecords = null;
		 try
		    {
		      if(memberId == null)
		      {
		    	  allRecords = db.getAllDocsRequestBuilder().includeDocs(true).build().getResponse().getDocsAs(MyWorkoutRecords.class);
		      }else{

		          // create Index
		          // Here is create a design doc named designdoc
		          // A view named querybymemberIdView
		          // and an index named memberId
				      db.createIndex("querybymemberIdView","designdoc","json",
						      new IndexField[]{
		                	new IndexField("memberId",SortOrder.asc)
		              }
				      );
		          System.out.println("Successfully created index");
		          //allRecords = db.findByIndex("{\"memberId\" :\"" + memberId + "\"}", Review.class);
		          allRecords = db.findByIndex("{\"memberId\" : \""+memberId+"\"}", MyWorkoutRecords.class);
		          System.out.println(allRecords);
		          System.out.println("{\"memberId\" : "+memberId+"}");
//		          allRecords = db.findByIndex("{\"memberId\" : \"001\"}", MyWorkoutRecords.class);
		      }


		    } catch (Exception e) {
						System.out.println("Exception thrown : " + e.getMessage());
				}
//		allRecords= db.findByIndex("{\"memberId\" : " + new MyWorkoutRecords(true).getMemberId() + "}", MyWorkoutRecords.class);
		return allRecords;
//		return Response.ok(allRecords).header("Access-Control-Allow-Origin", "*").build();
		
	}
	
	@RequestMapping(method=RequestMethod.GET, produces="application/json")
	@CrossOrigin
	public @ResponseBody ResponseEntity<List<MyWorkoutRecords>> getWorkoutRecords(@RequestParam(required=false) String memberId) {
		List<MyWorkoutRecords> allRecords = null;
		 try
		    {
		      if(memberId == null)
		      {
		    	  allRecords = db.getAllDocsRequestBuilder().includeDocs(true).build().getResponse().getDocsAs(MyWorkoutRecords.class);
		      }else{

		          // create Index
		          // Here is create a design doc named designdoc
		          // A view named querybymemberIdView
		          // and an index named memberId
				      db.createIndex("querybymemberIdView","designdoc","json",
						      new IndexField[]{
		                	new IndexField("memberId",SortOrder.asc)
		              }
				      );
		          System.out.println("Successfully created index");
		          //allRecords = db.findByIndex("{\"memberId\" :\"" + memberId + "\"}", Review.class);
		          allRecords = db.findByIndex("{\"memberId\" : \""+memberId+"\"}", MyWorkoutRecords.class);
		          System.out.println(allRecords);
		          System.out.println("{\"memberId\" : "+memberId+"}");
//		          allRecords = db.findByIndex("{\"memberId\" : \"001\"}", MyWorkoutRecords.class);
		      }


		    } catch (Exception e) {
						System.out.println("Exception thrown : " + e.getMessage());
				}
	
		 HttpHeaders header = new HttpHeaders();
		 header.add("Access-Control-Allow-Origin", "*");
		 header.add("memberId", memberId);
		 
		 return new ResponseEntity<List<MyWorkoutRecords>>(allRecords, header, HttpStatus.OK);
	}	
		
}
