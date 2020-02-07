package com.distribute.dsc.service;

import com.distribute.dsc.handler.RegisterCompanyHandler;
import com.distribute.dsc.model.UserResponse;


public interface CompanyService {
    UserResponse registerCompany(RegisterCompanyHandler requestBody);
}
