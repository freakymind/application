package com.dsc.service;

import com.dsc.handler.RegisterCompanyHandler;
import com.dsc.response.UserResponse;

public interface RegisterCompanyService {

	UserResponse registerCompany(RegisterCompanyHandler requestBody) throws Exception;

	UserResponse getregisterCompany() throws Exception;

}
