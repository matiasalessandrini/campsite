package com.truenorth.campsite.booking.depository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.truenorth.campsite.booking.model.BookedSpot;

@Repository
public interface BookedSpotRepository extends JpaRepository<BookedSpot, Long> {

	@Query("select bs from BookedSpot bs "
			+ "where bs.booking.campsite.name = :campsiteName "
			+ "and bs.date between :arrivalDate and :departureDate")
	List<BookedSpot> findAllByCampsiteBetweenArrivalDateAndDepartureDate(
			@Param("campsiteName") String campsiteName,
			@Param("arrivalDate") Date arrivalDate,
			@Param("departureDate") Date departureDate
	);


}
