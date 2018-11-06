package com.truenorth.campsite.campsite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.truenorth.campsite.campsite.model.Campsite;

@Repository
public interface CampsiteRepository extends JpaRepository<Campsite, Long>{
	
	Campsite findOneByName(String name);

}
