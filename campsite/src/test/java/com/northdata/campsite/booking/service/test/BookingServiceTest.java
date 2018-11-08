package com.northdata.campsite.booking.service.test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.truenorth.campsite.CampsiteApplication;
import com.truenorth.campsite.booking.exception.SpotBookedException;
import com.truenorth.campsite.booking.model.BookedSpot;
import com.truenorth.campsite.booking.model.Booking;
import com.truenorth.campsite.booking.model.BookingStatus;
import com.truenorth.campsite.booking.model.BookingStatusEnum;
import com.truenorth.campsite.booking.repository.BookingStatusRepository;
import com.truenorth.campsite.booking.service.BookingService;
import com.truenorth.campsite.campsite.model.Campsite;
import com.truenorth.campsite.campsite.model.CampsiteName;
import com.truenorth.campsite.campsite.service.CampsiteService;
import com.truenorth.campsite.date.util.DateUtil;
import com.truenorth.campsite.exception.BusinessException;
import com.truenorth.campsite.spot.model.Spot;
import com.truenorth.campsite.user.model.User;
import com.truenorth.campsite.user.service.UserService;

@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { CampsiteApplication.class })
public class BookingServiceTest {

	@Autowired
	private BookingService bookingService;

	@Autowired
	private BookingStatusRepository bookingStatusRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private CampsiteService campsiteService;

	private String identifier1;
	private String identifier2;

	private Campsite campsite;
	
	private Spot spot;

	private BookingStatus bookingStatus;

	private User user1;
	private User user2;

	@Before
	public void setUp() {

		identifier1 = "A123456789";
		identifier2 = "B123456789";

		campsite = campsiteService.getByName(CampsiteName.VOLCANO.name());
		spot = campsite.getSpotList().get(0);
		user1 = userService.processRegistration("Jhon", "Doe", "jhondoe@gmail.com");
		user2 = userService.processRegistration("Jhon", "Snow", "jhonsnow@gmail.com");

		bookingStatus = bookingStatusRepository.findOneByCode(BookingStatusEnum.ACTIVE.name());

	}

	@Test(expected = SpotBookedException.class)
	public void testAddTwoBookingsWithSameSpotAndDate() throws BusinessException {

		LocalDateTime now = LocalDateTime.now();
		LocalDate bookingDate = LocalDate.now().plusDays(2);
	
		BookedSpot bookedSpot1 = new BookedSpot(spot, DateUtil.asDate(bookingDate));
		List<BookedSpot> bookedSpotList1 = new ArrayList<>();
		bookedSpotList1.add(bookedSpot1);
		Booking booking1 = new Booking(user1, campsite, identifier1, DateUtil.asDate(now), bookingStatus);
		booking1.setBookedSpotList(bookedSpotList1);
		bookingService.persist(booking1);

		BookedSpot bookedSpot2 = new BookedSpot(spot, DateUtil.asDate(bookingDate));
		Booking booking2 = new Booking(user2, campsite, identifier2, DateUtil.asDate(now), bookingStatus);
		List<BookedSpot> bookedSpotList2 = new ArrayList<>();
		bookedSpotList2.add(bookedSpot2);
		booking2.setBookedSpotList(bookedSpotList2);
		bookingService.persist(booking2);
	}

}
