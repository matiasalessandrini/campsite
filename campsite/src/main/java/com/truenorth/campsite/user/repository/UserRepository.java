package com.truenorth.campsite.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.truenorth.campsite.user.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	User getByEmail(String email);
	
}
