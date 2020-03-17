package com.dsc.service;

import com.dsc.handler.RegisterCompanyHandler;
import com.dsc.response.UserResponse;

public interface ProductService {
	UserResponse addProduct(RegisterCompanyHandler requestBody) throws Exception;
	UserResponse updateProduct(RegisterCompanyHandler requestBody) throws Exception;

}
