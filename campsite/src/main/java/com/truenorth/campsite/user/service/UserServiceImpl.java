package com.truenorth.campsite.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.truenorth.campsite.user.model.User;
import com.truenorth.campsite.user.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public User processRegistration(String firstName, String lastName, String email) {
		User user = userRepository.getByEmail(email);
		if (user == null) {
			user = new User(firstName, lastName, email);
			userRepository.save(user);
		}
		return user;
	}

	
}
