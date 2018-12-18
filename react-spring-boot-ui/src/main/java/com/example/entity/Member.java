package com.example.entity;

public class Member {

	private String _rev;
	private String _id;
	private String memberId;
	private String firstName;
	private String lastName;
	private String gender;
	private String contact;
	private String address;

	public Member() {
		super();
	}



	public Member(String memberId, String firstName, String lastName, String gender, String contact, String address) {
		super();
		this.memberId = memberId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.contact = contact;
		this.address = address;
	}



	public Member(boolean isStub) {
		this.memberId = "101";
		this.firstName = "Manjul";
		this.lastName = "Srivastava";
		this.gender = "Male";
		this.contact = "9002902902";
		this.address = "H-42/A, Subhash Colony, Tagore Road, Kanpur - 208001";
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	
	
	public String getMemberId() {
		return memberId;
	}


	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}



	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "Member [_rev=" + _rev + ", _id=" + _id + ", memberId=" + memberId + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", gender=" + gender + ", contact=" + contact + ", Address=" + address
				+ "]";
	}



}
