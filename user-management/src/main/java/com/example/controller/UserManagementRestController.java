package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cloudant.client.api.Database;
import com.cloudant.client.api.model.IndexField;
import com.cloudant.client.api.model.Response;
import com.example.entity.Member;
import com.cloudant.client.api.model.IndexField.SortOrder;

@RestController
@RefreshScope
public class UserManagementRestController {
	
	@Autowired
	private Database db;
	
	
	@RequestMapping(method=RequestMethod.POST, consumes="application/json")
	@CrossOrigin
	public @ResponseBody String saveMemberRecords(@RequestBody Member member) {
		
		String memberId = new ManageMemberIdController().getMemberId();
		member.setMemberId(memberId);
		System.out.println("Member Record : "+member);
		
		Response r = null;
		if(member!=null) {
			r =db.post(member);
		}
		memberId = Integer.toString((Integer.parseInt(memberId)+1));
		System.out.println("updated memberId in table : "+(new ManageMemberIdController()).updateMemberId(memberId) );
		
		return r.getId();
	}
		
	@RequestMapping("/initialize")
	@CrossOrigin
	public @ResponseBody List<Member> Initialize() {
//		db.remove(new MyWorkoutRecords(true));
		System.out.println("Initializing the db :" +db.post(new Member(true)));
		return db.findByIndex("{\"memberId\" : " + new Member(true).getMemberId() + "}", Member.class);
	}
	
	@RequestMapping(value="/list" ,method=RequestMethod.GET, produces="application/json")
	public @ResponseBody List<Member> getMembersRecordsList(@RequestParam(required=false) String memberId) {
		System.out.println("MemberId getting :"+memberId);
		List<Member> allRecords = null;
		 try
		    {
		      if(memberId == null)
		      {
		    	  allRecords = db.getAllDocsRequestBuilder().includeDocs(true).build().getResponse().getDocsAs(Member.class);
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
		          System.out.println("Successfully created members db index");
		          //allRecords = db.findByIndex("{\"memberId\" :\"" + memberId + "\"}", Review.class);
		          allRecords = db.findByIndex("{\"memberId\" : \""+memberId+"\"}", Member.class);
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
	public @ResponseBody ResponseEntity<List<Member>> getMembersRecords(@RequestParam(required=false) String memberId) {
		List<Member> allRecords = null;
		 try
		    {
		      if(memberId == null)
		      {
		    	  allRecords = db.getAllDocsRequestBuilder().includeDocs(true).build().getResponse().getDocsAs(Member.class);
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
		          System.out.println("Successfully created members db index");
		          //allRecords = db.findByIndex("{\"memberId\" :\"" + memberId + "\"}", Review.class);
		          allRecords = db.findByIndex("{\"memberId\" : \""+memberId+"\"}", Member.class);
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
		 
		 return new ResponseEntity<List<Member>>(allRecords, header, HttpStatus.OK);
	}	
	
	@RequestMapping(value="/getid", method=RequestMethod.GET, produces="application/json")
	@CrossOrigin
	public @ResponseBody ResponseEntity<String> getMemberIdByName(@RequestParam(required=false) String firstName) {
		List<Member> allRecords = null;
		 try
		    {
		      if(firstName == null)
		      {
		    	  allRecords = db.getAllDocsRequestBuilder().includeDocs(true).build().getResponse().getDocsAs(Member.class);
		      }else{

		          // create Index
		          // Here is create a design doc named designdoc
		          // A view named querybymemberIdView
		          // and an index named memberId
				      db.createIndex("querybyfisrtNameView","designdoc","json",
						      new IndexField[]{
		                	new IndexField("firstName",SortOrder.asc)
		              }
				      );
		          System.out.println("Successfully created members db index by member firstName");
		          allRecords = db.findByIndex("{\"firstName\" : \""+firstName+"\"}", Member.class);
		          System.out.println(allRecords);
		          System.out.println("{\"firstName\" : "+firstName+"}");
		      }


		    } catch (Exception e) {
						System.out.println("Exception thrown : " + e.getMessage());
				}
	
		 HttpHeaders header = new HttpHeaders();
		 header.add("Access-Control-Allow-Origin", "*");
		 header.add("firstName", firstName);
		 
		 return new ResponseEntity<String>("memberId: "+allRecords.get(0).getMemberId(), header, HttpStatus.OK);
	}
}
