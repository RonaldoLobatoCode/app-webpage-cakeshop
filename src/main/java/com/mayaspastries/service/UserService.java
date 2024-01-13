package com.mayaspastries.service;

import com.mayaspastries.entities.User;

public interface UserService {

	public User getUserAndPassword(String user, String password);
	
	User getUser(String username, String providedPassword);
}
