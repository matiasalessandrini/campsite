package com.northdata.campsite.booking.controller.test;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.truenorth.campsite.CampsiteApplication;
import com.truenorth.campsite.booking.model.BookedSpot;
import com.truenorth.campsite.booking.model.Booking;
import com.truenorth.campsite.booking.model.BookingStatus;
import com.truenorth.campsite.booking.model.BookingStatusEnum;
import com.truenorth.campsite.booking.repository.BookingRepository;
import com.truenorth.campsite.booking.repository.BookingStatusRepository;
import com.truenorth.campsite.campsite.model.Campsite;
import com.truenorth.campsite.campsite.model.CampsiteName;
import com.truenorth.campsite.campsite.service.CampsiteService;
import com.truenorth.campsite.date.util.DateUtil;
import com.truenorth.campsite.spot.model.Spot;
import com.truenorth.campsite.user.model.User;
import com.truenorth.campsite.user.service.UserService;

@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { CampsiteApplication.class })
@AutoConfigureMockMvc
public class BookingControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private BookingRepository bookingRepository;
	
	@Autowired
	private CampsiteService campsiteService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private BookingStatusRepository bookingStatusRepository;

	private String identifier;
	private String firstName;
	private String lastName;
	private String email;
	
	private Booking booking;

	@Before
	public void setUp() {

		identifier = "A123456789";
		firstName = "Jhon";
		lastName = "Doe";
		email = "jhondoe@gmail.com";
		
		Campsite campsite = campsiteService.getByName(CampsiteName.VOLCANO.name());
		User user = userService.processRegistration(firstName, lastName, email);
		BookingStatus bookingStatus = bookingStatusRepository.findOneByCode(BookingStatusEnum.ACTIVE.name());
		Spot spot = campsite.getSpotList().get(0);
		BookedSpot bookedSpot = new BookedSpot(spot, DateUtil.asDate(LocalDate.now()));
		List<BookedSpot> bookedSpotList = new ArrayList<>();
		bookedSpotList.add(bookedSpot);
		booking = new Booking(user,campsite,identifier, DateUtil.asDate(LocalDateTime.now()),bookingStatus);
		booking.setBookedSpotList(bookedSpotList);
		
		Mockito.when(bookingRepository.findOneByIdentifier(identifier)).thenReturn(booking);
	}

	@Test
	public void testAddBooking() throws Exception {

		String arrivalDate = LocalDate.now().plusDays(2).toString();
		String departureDate = LocalDate.now().plusDays(4).toString();

		mockMvc
				.perform(put("/booking?arrivalDate=" + arrivalDate 
						+ "&departureDate=" + departureDate 
						+ "&firstName=" + firstName 
						+ "&lastName=" + lastName 
						+ "&email=" + email)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());

	}

	@Test
	public void testEditBooking() throws Exception {

		String arrivalDate = LocalDate.now().plusDays(4).toString();
		String departureDate =  LocalDate.now().plusDays(6).toString();

		 mockMvc.perform(post("/booking/" + identifier + "?arrivalDate=" + arrivalDate 
				+ "&departureDate=" + departureDate) 
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
	
	
	@Test
	public void testEditBookingWrongDates1() throws Exception {

		String arrivalDate = LocalDate.now().toString();
		String departureDate =  LocalDate.now().plusDays(1).toString();

		String errorMessage = mockMvc.perform(post("/booking/" + identifier + "?arrivalDate=" + arrivalDate 
				+ "&departureDate=" + departureDate) 
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotAcceptable()).andReturn().getResponse().getContentAsString();
		
		assertNotNull(errorMessage);

	}
	
	@Test
	public void testEditBookingWrongDates2() throws Exception {

		String arrivalDate = LocalDate.now().plusDays(40).toString();
		String departureDate =  LocalDate.now().plusDays(42).toString();

		String errorMessage = mockMvc.perform(post("/booking/" + identifier + "?arrivalDate=" + arrivalDate 
				+ "&departureDate=" + departureDate) 
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotAcceptable()).andReturn().getResponse().getContentAsString();
		
		assertNotNull(errorMessage);

	}
	
	
	@Test
	public void testEditBookingWrongDates3() throws Exception {

		String arrivalDate = LocalDate.now().plusDays(2).toString();
		String departureDate =  LocalDate.now().plusDays(2).toString();

		String errorMessage =mockMvc.perform(post("/booking/" + identifier + "?arrivalDate=" + arrivalDate 
				+ "&departureDate=" + departureDate) 
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotAcceptable()).andReturn().getResponse().getContentAsString();
		
		assertNotNull(errorMessage);

	}
	
	@Test
	public void testEditBookingWrongDates4() throws Exception {

		String arrivalDate = LocalDate.now().plusDays(2).toString();
		String departureDate =  LocalDate.now().plusDays(6).toString();

		String errorMessage =mockMvc.perform(post("/booking/" + identifier + "?arrivalDate=" + arrivalDate 
				+ "&departureDate=" + departureDate) 
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotAcceptable()).andReturn().getResponse().getContentAsString();
		
		assertNotNull(errorMessage);

	}
	
	@Test
	public void testDeleteBooking() throws Exception {

		 mockMvc.perform(delete("/booking/" + identifier) 
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

}
