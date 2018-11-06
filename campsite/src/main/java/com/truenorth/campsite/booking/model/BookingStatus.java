package com.truenorth.campsite.booking.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class BookingStatus {
	
	@Id
	@GeneratedValue
	private Long id;
	
	private String code;

	public String getCode() {
		return code;
	}

}
