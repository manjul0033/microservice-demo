package com.example.entity;

import java.util.ArrayList;
import java.util.List;

public class MyWorkoutRecords {
	
	private String _rev;
	private String _id;
	private String workoutId;
	private String memberId;
	private String date;
	private String time;
	private String workoutName;
	private String workoutType;
	private List<String> pulse;
	private List<String> rpm;
	private List<String> speed;
	
	public MyWorkoutRecords() {}
	
	public MyWorkoutRecords(boolean init) {
		super();
		this.workoutId = "100001";
		this.memberId = "101";
		this.date = "06/12/2017";
		this.time = "17:50";
		this.workoutName = "Cycling";
		this.workoutType = "Cardio";
		this.pulse = new ArrayList<String>(); pulse.add("145");pulse.add("168");pulse.add("156");
		this.rpm = new ArrayList<String>(); rpm.add("89");rpm.add("86");rpm.add("65");
		this.speed = new ArrayList<String>(); speed.add("10km/hr");speed.add("12km/hr");speed.add("9km/hr");
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getWorkoutId() {
		return workoutId;
	}

	public void setWorkoutId(String workoutId) {
		this.workoutId = workoutId;
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

	public List<String> getPulse() {
		return pulse;
	}

	public void setPulse(List<String> pulse) {
		this.pulse = pulse;
	}

	public List<String> getRpm() {
		return rpm;
	}

	public void setRpm(List<String> rpm) {
		this.rpm = rpm;
	}

	public List<String> getSpeed() {
		return speed;
	}

	public void setSpeed(List<String> speed) {
		this.speed = speed;
	}

	@Override
	public String toString() {
		return "MyWorkoutRecords [_rev=" + _rev + ", _id=" + _id + ", workoutId=" + workoutId + ", memberId=" + memberId
				+ ", date=" + date + ", time=" + time + ", workoutName=" + workoutName + ", workoutType=" + workoutType
				+ ", pulse=" + pulse + ", rpm=" + rpm + ", speed=" + speed + "]";
	}
	
}
