package com.truenorth.campsite.booking.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.truenorth.campsite.booking.model.Identifier;
import com.truenorth.campsite.booking.service.BookingService;
import com.truenorth.campsite.campsite.model.Campsite;
import com.truenorth.campsite.campsite.model.CampsiteName;
import com.truenorth.campsite.campsite.service.CampsiteService;
import com.truenorth.campsite.exception.BusinessException;
import com.truenorth.campsite.user.model.User;
import com.truenorth.campsite.user.service.UserService;

@RestController
@RequestMapping(value = "/booking")
public class BookingController {

	@Autowired
	private BookingService bookingService;

	@Autowired
	private UserService userService;

	@Autowired
	private CampsiteService campsiteService;

	@RequestMapping(method = RequestMethod.PUT)
	public Identifier addBooking(@RequestParam("arrivalDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date arrivalDate,
			@RequestParam("departureDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date departureDate, 
			@RequestParam("firstName") String firstName,
			@RequestParam("lastName") String lastName, 
			@RequestParam("email") String email) throws BusinessException {

		Campsite campsite = campsiteService.getByName(CampsiteName.VOLCANO.name());
		User user = userService.processRegistration(firstName, lastName, email);
		return bookingService.addBooking(arrivalDate, departureDate, campsite, user);
	}

	@RequestMapping(path = "/{identifier}", method = RequestMethod.POST)
	public void editBooking(@PathVariable("identifier") String identifier,
			@RequestParam("arrivalDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date arrivalDate,
			@RequestParam("departureDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date departureDate) throws BusinessException {

		bookingService.editBooking(arrivalDate, departureDate, identifier);
	}

	@RequestMapping(path = "/{identifier}", method = RequestMethod.DELETE)
	public void cancelBooking(@PathVariable("identifier") String identifier) throws BusinessException {

		bookingService.cancelBooking(identifier);
	}

}
