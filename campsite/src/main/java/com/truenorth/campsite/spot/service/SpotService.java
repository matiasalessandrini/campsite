package com.truenorth.campsite.spot.service;

import java.time.LocalDate;

import com.truenorth.campsite.campiste.exception.CampsiteFullException;
import com.truenorth.campsite.campsite.model.Campsite;
import com.truenorth.campsite.spot.model.Spot;


public interface SpotService {

	Spot getAvailableSpotByDate(LocalDate localDate, Campsite campsite) throws CampsiteFullException;
	
}
