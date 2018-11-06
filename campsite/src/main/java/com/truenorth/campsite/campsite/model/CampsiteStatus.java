package com.truenorth.campsite.campsite.model;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum CampsiteStatus{
	
	AVAILABLE("available"), 
	FULL("full");
	
	private String status;
	

	public String getStatus() {
		return status;
	}

	CampsiteStatus(String status) {
        this.status = status;
    }
	
}
