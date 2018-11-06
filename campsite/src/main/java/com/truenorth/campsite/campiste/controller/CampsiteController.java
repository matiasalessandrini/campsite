package com.truenorth.campsite.campiste.controller;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.truenorth.campsite.campsite.model.Campsite;
import com.truenorth.campsite.campsite.model.CampsiteName;
import com.truenorth.campsite.campsite.model.CampsiteStatus;
import com.truenorth.campsite.campsite.service.CampsiteService;

@RestController
@RequestMapping(value = "/availability")
public class CampsiteController {
	
	@Autowired
	private CampsiteService campsiteService;

	@RequestMapping(method = RequestMethod.GET)
	@Cacheable("campsiteStatus")
	public CampsiteStatus getAvailability(@RequestParam("fromDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Optional<Date> fromDate, 
			@RequestParam("toDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Optional<Date> toDate) {
		
		CampsiteStatus result;
		Campsite campsite = campsiteService.getByName(CampsiteName.VOLCANO.name());
		if (fromDate.isPresent() && toDate.isPresent()) {
			result = campsiteService.getCampsiteStatus(fromDate.get(), toDate.get(), campsite);
		}
		else {
			result = campsiteService.getCampsiteStatus(null, null, campsite);
		}
		return result;
	}
	
}
