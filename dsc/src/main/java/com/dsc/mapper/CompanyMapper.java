package com.dsc.mapper;

import java.util.ArrayList;

import com.dsc.handler.RegisterCompanyHandler;
import com.dsc.model.RegisterCompany;
import com.dsc.model.RegisterCompany.Company;
import com.dsc.model.RegisterCompany.Distributor;
import com.dsc.model.RegisterCompany.User;

public class CompanyMapper {

	public static RegisterCompany mapAllCompanyDetails(RegisterCompanyHandler requestBody) {
		RegisterCompany allDetails = new RegisterCompany();
		Company syncCompanyDetails = syncCompanyDetails(requestBody);
		ArrayList<User> syncUserIddata = syncUseridData(requestBody);
		ArrayList<Distributor> syncDistributorDetails = syncDistributorDetails(requestBody);
		allDetails.setCompany(syncCompanyDetails);
		allDetails.setUserid(syncUserIddata);
		allDetails.setDistributor(syncDistributorDetails);
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

	private static ArrayList<User> syncUseridData(RegisterCompanyHandler requestBody) {
		ArrayList<User> userDetails = new ArrayList<>();
		User user = new User();
		user.setUser_id(requestBody.getUser_id());
		userDetails.add(user);
		return userDetails;
	}

	private static ArrayList<Distributor> syncDistributorDetails(RegisterCompanyHandler requestBody) {
		ArrayList<Distributor> distlist = new ArrayList<>();
		Distributor dist = new Distributor();
//		dist.setCompany_ref(requestBody.getCompany_ref());
		distlist.add(dist);
		return distlist;
	}

}
