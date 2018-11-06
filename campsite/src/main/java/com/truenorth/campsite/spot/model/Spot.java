package com.truenorth.campsite.spot.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.truenorth.campsite.campsite.model.Campsite;

@Entity
public class Spot {
	
	@Id
	@GeneratedValue
	private Long id;
	
	private Integer number;
	
	private Spot(){}
	
	public Spot(Integer number) {
		this.number = number;
	}
	
	@ManyToOne
	@JoinColumn(name = "campsite_id")
	private Campsite campsite;
	
	public Campsite getCampsite() {
		return campsite;
	}

	public void setCampsite(Campsite campsite) {
		this.campsite = campsite;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

}
