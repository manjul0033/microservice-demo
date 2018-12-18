package com.example.entity;

public class Reference {
	private String referenceId;

	public String getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}

	public Reference(String referenceId) {
		super();
		this.referenceId = referenceId;
	}

	@Override
	public String toString() {
		return "Reference [referenceId=" + referenceId
				+ "]";
	}
	
}
