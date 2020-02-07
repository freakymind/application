package com.distribute.dsc.mapper;

import com.distribute.dsc.handler.RegisterCompanyHandler;
import com.distribute.dsc.model.RegisterCompany;

import java.util.ArrayList;

public class CompanyMapper {

    public static RegisterCompany mapAllCompanyDetails(RegisterCompanyHandler requestBody){
        RegisterCompany allDetails = new RegisterCompany();
        ArrayList<RegisterCompany.User> userDetails = syncUserDetails(requestBody);
        RegisterCompany.Company companyDetails = syncCompanyDetails(requestBody);
        allDetails.setUser(userDetails);
        allDetails.setCompany(companyDetails);
        return allDetails;
    }


    private static ArrayList<RegisterCompany.User> syncUserDetails(RegisterCompanyHandler requestBody) {
        ArrayList<RegisterCompany.User> userDetails = new ArrayList<RegisterCompany.User>();
        RegisterCompany.User user = new  RegisterCompany.User();
        user.setAddress(requestBody.getAddress());
        user.setCountry(requestBody.getCountry());
        user.setEmail(requestBody.getEmail());
        user.setFullname(requestBody.getFullname());
        user.setMobile(requestBody.getMobile());
        user.setPassword(requestBody.getPassword());
        userDetails.add(user);
        return userDetails;
    }

    private static RegisterCompany.Company syncCompanyDetails(RegisterCompanyHandler requestBody){
        RegisterCompany.Company companyDetails = new RegisterCompany.Company();
        companyDetails.setCompany_address(requestBody.getCompany_address());
        companyDetails.setCompany_email(requestBody.getCompany_email());
        companyDetails.setCompany_mobile(requestBody.getCompany_mobile());
        companyDetails.setCompany_name(requestBody.getCompany_name());
        return companyDetails;
    }
}
