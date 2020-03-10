package com.dsc.mapper;

import java.util.ArrayList;

import com.dsc.handler.RegisterCompanyHandler;
import com.dsc.model.User;
import com.dsc.model.User.UserDetails;

public class UserMapper {

	public static User mapAllUserDetails(RegisterCompanyHandler requestBody) {
		User userDetails = new User();
		ArrayList<UserDetails> syncUserDetails = syncUserDetails(requestBody);
		userDetails.setUserdetails(syncUserDetails);
		return userDetails;
	}

	private static ArrayList<UserDetails> syncUserDetails(RegisterCompanyHandler requestBody) {
		ArrayList<UserDetails> userDetails = new ArrayList<>();
		UserDetails user = new UserDetails();
		user.setUser_address(requestBody.getUser_address());
		user.setUser_country(requestBody.getUser_country());
		user.setEmail(requestBody.getUser_email());
		user.setUser_name(requestBody.getUser_name());
		user.setUser_mobile(requestBody.getUser_mobile());
		user.setPassword(requestBody.getUser_password());
		userDetails.add(user);
		return userDetails;
	}
}
