package com.truenorth.campsite.user.service;

import com.truenorth.campsite.user.model.User;


public interface UserService {
	
	User processRegistration(String firstName, String lastName, String email);

}
