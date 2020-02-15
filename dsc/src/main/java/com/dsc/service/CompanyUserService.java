package com.dsc.service;

import com.dsc.handler.RegisterCompanyHandler;
import com.dsc.response.UserResponse;

public interface CompanyUserService {

	UserResponse registerCompanyUser(RegisterCompanyHandler requestBody) throws Exception;

	UserResponse updateCompanyUser(RegisterCompanyHandler requestBody) throws Exception;

}
