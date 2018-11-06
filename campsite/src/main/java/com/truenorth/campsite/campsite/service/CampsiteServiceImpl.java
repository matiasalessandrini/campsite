package com.truenorth.campsite.campsite.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.truenorth.campsite.booking.model.BookedSpot;
import com.truenorth.campsite.booking.service.BookingService;
import com.truenorth.campsite.campiste.exception.CampsiteFullException;
import com.truenorth.campsite.campsite.model.Campsite;
import com.truenorth.campsite.campsite.model.CampsiteStatus;
import com.truenorth.campsite.campsite.repository.CampsiteRepository;
import com.truenorth.campsite.date.util.DateUtil;

@Service
public class CampsiteServiceImpl implements CampsiteService {

	@Autowired
	private BookingService bookingService;

	@Autowired
	private CampsiteRepository campsiteRepository;
	
	@Value("${default.booking.search.months}")
	private Integer defaultBookingSearchMonths;
	
	@Value("${min.days.for.booking}")
	private Integer minDaysForBooking;

	@Override
	public CampsiteStatus getCampsiteStatus(Date fromDate, Date toDate, Campsite campsite) {
		
		CampsiteStatus result = CampsiteStatus.AVAILABLE;
		if (fromDate == null && toDate == null) {
			LocalDate from = LocalDate.now().plusDays(minDaysForBooking);
			LocalDate to = from.plusMonths(defaultBookingSearchMonths);
			fromDate = DateUtil.asDate(from);
			toDate = DateUtil.asDate(to);
		}
		try {
			validateAvailability(fromDate, toDate, campsite);
		} catch (CampsiteFullException e) {
			result = CampsiteStatus.FULL;
		}
		return result;
	}


	@Override
	public void validateAvailability(Date fromDate, Date toDate, Campsite campsite) throws CampsiteFullException {
		
		LocalDate from = DateUtil.asLocalDate(fromDate);
		LocalDate arrival = DateUtil.asLocalDate(toDate);
		List<LocalDate> dates = Stream.iterate(from, date -> date.plusDays(1))
			    .limit(ChronoUnit.DAYS.between(from, arrival))
			    .collect(Collectors.toList());
		
		List<BookedSpot> bookedSpotList = bookingService.findAllBookedSpotByCampsiteAndDate(fromDate, toDate, campsite);
		int spotCount = campsite.getSpotList().size();
		for (LocalDate localDate : dates) {
			long bookedSpotCount = bookedSpotList.stream().filter(bs->bs.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().equals(localDate)).count();
			if (bookedSpotCount >= spotCount) {
				throw new CampsiteFullException(); 
			}
		}
	}
	

	@Override
	public Campsite getByName(String name) {
		return campsiteRepository.findOneByName(name);
	}

}
