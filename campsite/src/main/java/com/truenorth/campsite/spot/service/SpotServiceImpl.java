package com.truenorth.campsite.spot.service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.truenorth.campsite.campiste.exception.CampsiteFullException;
import com.truenorth.campsite.campsite.model.Campsite;
import com.truenorth.campsite.date.util.DateUtil;
import com.truenorth.campsite.spot.model.Spot;
import com.truenorth.campsite.spot.repository.SpotRepository;


@Service
public class SpotServiceImpl implements SpotService {

	@Autowired
	private SpotRepository spotRepository;


	@Override
	public Spot getAvailableSpotByDate(LocalDate localDate, Campsite campsite) throws CampsiteFullException {
		
		Date start = DateUtil.asDate(localDate);
		Date end = DateUtil.asDate(localDate.plusDays(1));
		List<Spot> spotList = spotRepository.findAllSpotsByCampsite(campsite.getName());
		List<Spot> bookedSpotList = spotRepository.findAllBookedByCampsiteAndDate(start, end, campsite.getName());
		spotList.removeAll(bookedSpotList);
		if (spotList.isEmpty()) {
			throw new CampsiteFullException();
		}
		return spotList.get(0);
	}
	
}
