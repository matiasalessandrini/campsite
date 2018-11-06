package com.truenorth.campsite.campsite.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.truenorth.campsite.spot.model.Spot;

@Entity
public class Campsite{

	@Id
	@GeneratedValue
	private Long id;

	private String name;

	@OneToMany(mappedBy = "campsite", fetch = FetchType.EAGER)
	private List<Spot> spotList;

	private Campsite() {
	}

	public Campsite(String name) {
		this.name = name;
	}
	
	public List<Spot> getSpotList() {
		return spotList;
	}

	public void setSpotList(List<Spot> spotList) {
		this.spotList = spotList;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
