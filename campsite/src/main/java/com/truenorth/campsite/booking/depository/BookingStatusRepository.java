package com.truenorth.campsite.booking.depository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.truenorth.campsite.booking.model.BookingStatus;

@Repository
public interface BookingStatusRepository extends JpaRepository<BookingStatus, Long>{
	
	BookingStatus findOneByCode(String statusCode);
	
}
