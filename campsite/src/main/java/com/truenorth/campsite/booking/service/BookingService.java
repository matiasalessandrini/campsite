package com.truenorth.campsite.booking.service;

import java.util.Date;
import java.util.List;

import com.truenorth.campsite.booking.exception.SpotBookedException;
import com.truenorth.campsite.booking.model.BookedSpot;
import com.truenorth.campsite.booking.model.Booking;
import com.truenorth.campsite.booking.model.Identifier;
import com.truenorth.campsite.campsite.model.Campsite;
import com.truenorth.campsite.exception.BusinessException;
import com.truenorth.campsite.user.model.User;


public interface BookingService {
	
	List<BookedSpot> findAllBookedSpotByCampsiteAndDate(Date arrivalDate, Date departureDate, Campsite campsite);
	
	Identifier addBooking(Date arrivalDate, Date departureDate, Campsite campsite, User user) throws BusinessException;
	
	void editBooking(Date arrivalDate, Date departureDate, String identifier) throws BusinessException;
	
	void cancelBooking(String identifier) throws BusinessException;

	void persist(Booking booking) throws SpotBookedException;

}
