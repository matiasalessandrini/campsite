package com.truenorth.campsite.booking.depository;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.truenorth.campsite.booking.model.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

	@Lock(LockModeType.OPTIMISTIC)
	@Transactional(readOnly=true)
	Booking findOneByIdentifier(String identifier);
	
	@Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
	Booking save(Booking booking);

}
