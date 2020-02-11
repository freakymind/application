package com.dsc.mapper;

import java.util.ArrayList;

import com.dsc.handler.RegisterCompanyHandler;
import com.dsc.model.RegisterCompany;
import com.dsc.model.RegisterCompany.Company;
import com.dsc.model.RegisterCompany.User;

public class CompanyMapper {

	public static RegisterCompany mapAllCompanyDetails(RegisterCompanyHandler requestBody) {
		RegisterCompany allDetails = new RegisterCompany();
		Company syncCompanyDetails = syncCompanyDetails(requestBody);
		ArrayList<User> syncUserDetails = syncUserDetails(requestBody);
		allDetails.setCompany(syncCompanyDetails);
		allDetails.setUser(syncUserDetails);
		return allDetails;

	}

	private static Company syncCompanyDetails(RegisterCompanyHandler requestBody) {
		Company companyDetails = new Company();
		companyDetails.setCompany_address(requestBody.getCompany_address());
		companyDetails.setEmail(requestBody.getCompany_email());
		companyDetails.setCompany_mobile(requestBody.getCompany_mobile());
		companyDetails.setCompany_name(requestBody.getCompany_name());
		return companyDetails;
	}

	private static ArrayList<User> syncUserDetails(RegisterCompanyHandler requestBody) {
		ArrayList<User> userDetails = new ArrayList<>();
		User user = new User();
		user.setAddress(requestBody.getAddress());
		user.setCountry(requestBody.getCountry());
		user.setEmail(requestBody.getEmail());
		user.setFullname(requestBody.getFullname());
		user.setMobile(requestBody.getMobile());
		user.setPassword(requestBody.getPassword());
		userDetails.add(user);
		return userDetails;
	}
}
