package com.truenorth.campsite.spot.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.truenorth.campsite.spot.model.Spot;

@Repository
public interface SpotRepository extends JpaRepository<Spot, Long> {

	@Query("select bs.spot from BookedSpot bs  "
			+ "where bs.spot.campsite.name = :campsiteName "
			+ "and bs.date between :start and :end")
	List<Spot> findAllBookedByCampsiteAndDate(@Param("start") Date start, @Param("end") Date end, @Param("campsiteName") String campsiteName);

	@Query("select s from Spot s where s.campsite.name = :campsiteName")
	List<Spot> findAllSpotsByCampsite(@Param("campsiteName") String campsiteName);
	
}
