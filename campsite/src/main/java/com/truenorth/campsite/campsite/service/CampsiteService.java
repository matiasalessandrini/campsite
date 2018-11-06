package com.truenorth.campsite.campsite.service;

import java.util.Date;

import com.truenorth.campsite.campiste.exception.CampsiteFullException;
import com.truenorth.campsite.campsite.model.Campsite;
import com.truenorth.campsite.campsite.model.CampsiteStatus;


public interface CampsiteService {
	
	CampsiteStatus getCampsiteStatus(Date fromDate, Date toDate, Campsite campsite);

	Campsite getByName(String name);

	void validateAvailability(Date startDate, Date endDate, Campsite campsite) throws CampsiteFullException;

}
