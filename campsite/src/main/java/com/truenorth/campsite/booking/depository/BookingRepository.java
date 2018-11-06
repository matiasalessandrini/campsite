package com.truenorth.campsite.booking.depository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.truenorth.campsite.booking.model.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

	Booking findOneByIdentifier(String identifier);

}
