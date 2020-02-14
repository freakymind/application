package com.dsc.service;

import com.dsc.handler.RegisterCompanyHandler;
import com.dsc.response.UserResponse;

public interface DistributorService {

	UserResponse registerDistributor(RegisterCompanyHandler requestBody) throws Exception;

}
