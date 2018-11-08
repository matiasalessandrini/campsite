package com.truenorth.campsite.booking.service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.OptimisticLockException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.truenorth.campsite.booking.exception.BookingCancelledException;
import com.truenorth.campsite.booking.exception.BookingModifiedException;
import com.truenorth.campsite.booking.exception.InvalidIdentifierException;
import com.truenorth.campsite.booking.exception.SpotBookedException;
import com.truenorth.campsite.booking.model.BookedSpot;
import com.truenorth.campsite.booking.model.Booking;
import com.truenorth.campsite.booking.model.BookingStatus;
import com.truenorth.campsite.booking.model.BookingStatusEnum;
import com.truenorth.campsite.booking.model.Identifier;
import com.truenorth.campsite.booking.repository.BookedSpotRepository;
import com.truenorth.campsite.booking.repository.BookingRepository;
import com.truenorth.campsite.booking.repository.BookingStatusRepository;
import com.truenorth.campsite.campiste.exception.CampsiteFullException;
import com.truenorth.campsite.campsite.model.Campsite;
import com.truenorth.campsite.campsite.service.CampsiteService;
import com.truenorth.campsite.date.util.DateUtil;
import com.truenorth.campsite.exception.BusinessError;
import com.truenorth.campsite.exception.BusinessException;
import com.truenorth.campsite.spot.model.Spot;
import com.truenorth.campsite.spot.service.SpotService;
import com.truenorth.campsite.user.model.User;

@Service
public class BookingServiceImpl implements BookingService {

	@Autowired
	private BookedSpotRepository bookedSpotRepository;

	@Autowired
	private BookingRepository bookingRepository;

	@Autowired
	private BookingStatusRepository bookingStatusRepository;

	@Autowired
	private CampsiteService campsiteService;

	@Autowired
	private SpotService spotService;

	@Value("${min.booking.days}")
	private Integer minBookingDays;

	@Value("${max.booking.days}")
	private Integer maxBookingDays;

	@Value("${min.days.for.booking}")
	private Integer minDaysForBooking;

	@Value("${max.months.for.booking}")
	private Integer maxMonthsForBooking;

	@Override
	public List<BookedSpot> findAllBookedSpotByCampsiteAndDate(Date arrivalDate, Date departureDate,
			Campsite campsite) {

		return bookedSpotRepository.findAllByCampsiteBetweenArrivalDateAndDepartureDate(campsite.getName(), arrivalDate,
				departureDate);
	}

	@Override
	public Identifier addBooking(Date arrivalDate, Date departureDate, Campsite campsite, User user)
			throws BusinessException {

		validateBooking(arrivalDate, departureDate, campsite);
		return createBooking(arrivalDate, departureDate, campsite, user);
	}

	private Identifier createBooking(Date arrivalDate, Date departureDate, Campsite campsite, User user)
			throws BusinessException{

		Identifier identifier = new Identifier(generateIdentifier());
		BookingStatus status = bookingStatusRepository.findOneByCode(BookingStatusEnum.ACTIVE.name());
		Booking booking = new Booking(user, campsite, identifier.getId(), DateUtil.asDate(LocalDateTime.now()), status);
		booking.setBookedSpotList(getBookedSpots(arrivalDate, departureDate, booking));
		persist(booking);

		return identifier;
	}


	@Override
	@Transactional
	public void persist(Booking booking) throws BusinessException {
		try {
			bookingRepository.save(booking);
		} catch (OptimisticLockException ex) {
			throw new BookingModifiedException();
		} catch (DataIntegrityViolationException ex) {
			throw new SpotBookedException();
		}
	}
	
	private List<BookedSpot> getBookedSpots(Date arrivalDate, Date departureDate, Booking booking) throws CampsiteFullException{

		LocalDate arrival = DateUtil.asLocalDate(arrivalDate);
		LocalDate departure = DateUtil.asLocalDate(departureDate);
		List<LocalDate> dates = Stream.iterate(arrival, date -> date.plusDays(1))
				.limit(ChronoUnit.DAYS.between(arrival, departure)).collect(Collectors.toList());

		List<BookedSpot> list = new ArrayList<>();
		for (LocalDate localDate : dates) {
			Date date = DateUtil.asDate(localDate);
			Spot spot = spotService.getAvailableSpotByDate(localDate, booking.getCampsite());
			BookedSpot bookedSpot = new BookedSpot(spot, date);
			bookedSpot.setBooking(booking);
			list.add(bookedSpot);
		}
		
		return list;
	}

	@Override
	public void editBooking(Date arrivalDate, Date departureDate, String identifier) throws BusinessException {
		Booking booking = bookingRepository.findOneByIdentifier(identifier);
		if (booking != null) {
			if (!booking.getStatus().getCode().equals(BookingStatusEnum.CANCELLED.name())) {
				validateBooking(arrivalDate, departureDate, booking.getCampsite());
				modifyBooking(arrivalDate, departureDate, booking);
			}
			else {
				throw new BookingCancelledException();
			}
		} else {
			throw new InvalidIdentifierException();
		}
	}

	private void modifyBooking(Date arrivalDate, Date departureDate, Booking booking)
			throws BusinessException {

		booking.getBookedSpotList().clear();
		booking.getBookedSpotList().addAll(getBookedSpots(arrivalDate, departureDate, booking));
		booking.setModificationDate(DateUtil.asDate(LocalDateTime.now()));
		persist(booking);
	}

	@Override
	public void cancelBooking(String identifier) throws BusinessException {
		Booking booking = bookingRepository.findOneByIdentifier(identifier);
		if (booking != null) {
			booking.getBookedSpotList().clear();
			BookingStatus status = bookingStatusRepository.findOneByCode(BookingStatusEnum.CANCELLED.name());
			booking.setStatus(status);
			booking.setModificationDate(DateUtil.asDate(LocalDateTime.now()));
			persist(booking);
		} else {
			throw new InvalidIdentifierException();
		}
	}

	private void validateBooking(Date arrivalDate, Date departureDate, Campsite campsite) throws BusinessException {

		validateBookingDates(arrivalDate, departureDate);
		campsiteService.validateAvailability(arrivalDate, departureDate, campsite);
	}

	
	private void validateBookingDates(Date arrivalDate, Date departureDate) throws BusinessException {

		LocalDate arrival = DateUtil.asLocalDate(arrivalDate);
		LocalDate departure = DateUtil.asLocalDate(departureDate);

		LocalDate nowPlusXDays = LocalDate.now().plusDays(minDaysForBooking);
		if (nowPlusXDays.compareTo(arrival) > 0) {
			throw new BusinessException(BusinessError.ERROR_LATE_BOOKING, minDaysForBooking);
		}
		LocalDate nowPlusXmonths = LocalDate.now().plusMonths(maxMonthsForBooking);
		if (nowPlusXmonths.compareTo(arrival) < 0) {
			throw new BusinessException(BusinessError.ERROR_EARLY_BOOKING, maxMonthsForBooking);
		}
		long dayDifference = Duration.between(arrival.atStartOfDay(), departure.atStartOfDay()).toDays();
		if (dayDifference < minBookingDays) {
			throw new BusinessException(BusinessError.ERROR_MIN_BOOKING_DAYS, minBookingDays);
		}
		if (dayDifference > maxBookingDays) {
			throw new BusinessException(BusinessError.ERROR_MAX_BOOKING_DAYS, maxBookingDays);
		}
	}

	private String generateIdentifier() {
		return Long.toString(UUID.randomUUID().getLeastSignificantBits(), Character.MAX_RADIX).replace("-", "")
				.toUpperCase();
	}

}
