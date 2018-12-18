package com.example.entity;

import java.util.ArrayList;
import java.util.List;

public class WorkoutRecords2Send {
	
	private String memberId;
	private String date;
	private String time;
	private String workoutName;
	private String workoutType;
	
	public WorkoutRecords2Send() {}
	
	public WorkoutRecords2Send(boolean init) {
		super();
		//this.workoutId = "100001";
		this.memberId = "101";
		this.date = "06/12/2017";
		this.time = "17:50";
		this.workoutName = "Cycling";
		this.workoutType = "Cardio";
//		this.pulse = new ArrayList<String>(); pulse.add("145");pulse.add("168");pulse.add("156");
//		this.rpm = new ArrayList<String>(); rpm.add("89");rpm.add("86");rpm.add("65");
//		this.speed = new ArrayList<String>(); speed.add("10km/hr");speed.add("12km/hr");speed.add("9km/hr");
	}


	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getWorkoutName() {
		return workoutName;
	}

	public void setWorkoutName(String workoutName) {
		this.workoutName = workoutName;
	}

	public String getWorkoutType() {
		return workoutType;
	}

	public void setWorkoutType(String workoutType) {
		this.workoutType = workoutType;
	}

	

	@Override
	public String toString() {
		return "MyWorkoutRecords [memberId=" + memberId
				+ ", date=" + date + ", time=" + time + ", workoutName=" + workoutName + ", workoutType=" + workoutType
				+ "]";
	}
	
}
