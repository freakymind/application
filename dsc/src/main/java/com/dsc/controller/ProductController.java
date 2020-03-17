package com.dsc.controller;


import static com.dsc.constants.CompanyConstants.FAIL;
import static com.dsc.constants.CompanyConstants.REQUEST_EMPTY;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dsc.handler.RegisterCompanyHandler;
import com.dsc.response.ErrorResponse;
import com.dsc.response.UserResponse;
import com.dsc.serviceimpl.ProductServiceImpl;

@RestController
@RequestMapping("/service/products")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ProductController {
	@Autowired
	private ProductServiceImpl prodServiceImpl;

	private static final Logger logger = LoggerFactory.getLogger(RegisterCompanyController.class);
	ErrorResponse errorResponse = new ErrorResponse();

	@Secured({ "COMPANY_ADMIN", "COMPNAY_USER" })
	@PutMapping("/add_product")
	public ResponseEntity<Object> registerProduct(@RequestBody RegisterCompanyHandler requestBody,
			HttpServletRequest request, HttpServletResponse response) {
		logger.debug("Incoming request : " + requestBody);

		try {

			if (requestBody == null) {
				logger.error("Invalid request");
				errorResponse.setStatus(FAIL);
				errorResponse.setMessage("Invalid Request");
				return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
			}

			if (requestBody.getProduct_brand().isEmpty() || requestBody.getProduct_dimensions().isEmpty()
					|| requestBody.getProduct_model().isEmpty() || requestBody.getProduct_weight() == null
					|| requestBody.getCountry_code().isEmpty() || requestBody.getBatch_size() == null) {
				logger.error("Data must not be null");
				errorResponse.setMessage(REQUEST_EMPTY);
				errorResponse.setStatus(FAIL);
				errorResponse.setData(null);
				return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			UserResponse companyUserResp = prodServiceImpl.addProduct(requestBody);
			return new ResponseEntity<>(companyUserResp, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Exception caught : " + e.getMessage());
			errorResponse.setStatus(FAIL);
			errorResponse.setMessage("Exception caught Product controller!");
			return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);

		}

	}
	@Secured({ "COMPANY_ADMIN" })
	@PatchMapping("/update_product")
	public ResponseEntity<Object> UpdateProduct(@RequestBody RegisterCompanyHandler requestBody,
			HttpServletRequest request, HttpServletResponse response) {
		logger.debug("Incoming request : " + requestBody);
		try {
		if (requestBody == null) {
			logger.error("Invalid request");
			errorResponse.setStatus(FAIL);
			errorResponse.setMessage("Invalid Request");
			return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		
		if (( requestBody.getProduct_id().isEmpty())
				||requestBody.getProduct_name().isEmpty()||requestBody.getProduct_brand().isEmpty()
				||requestBody.getProduct_dimensions().isEmpty()||requestBody.getCountry_code().isEmpty()
				||requestBody.getProduct_model().isEmpty()||requestBody.getBatch().isEmpty()
				||requestBody.getBatch_size()==null
				||requestBody.getProduct_weight()==null) {
			logger.error("Data must not be null");
			errorResponse.setMessage(REQUEST_EMPTY);
			errorResponse.setStatus(FAIL);
			errorResponse.setData(null);
			return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		UserResponse productResp = prodServiceImpl.updateProduct(requestBody);
		return new ResponseEntity<>(productResp, HttpStatus.OK);

	} catch (Exception e) {
		logger.error("Exception caught : " + e.getMessage());
		errorResponse.setStatus(FAIL);
		errorResponse.setMessage("Exception caught Product update controller!");
		return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);

	}
}
	@Secured({ "COMPANY_ADMIN" })
	@GetMapping("/get_product")
	public ResponseEntity<Object> getProduct(@RequestBody RegisterCompanyHandler requestBody,
			HttpServletRequest request, HttpServletResponse response) {
		logger.debug("Incoming request : " + requestBody);
		try {
		if(requestBody==null) {
			logger.error("Invalid request");
			errorResponse.setStatus(FAIL);
			errorResponse.setMessage("Invalid Request");
			return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		if(requestBody.getProduct_id().isEmpty()) {
			
			logger.error("Data must not be null");
			errorResponse.setMessage(REQUEST_EMPTY);
			errorResponse.setStatus(FAIL);
			errorResponse.setData(null);
			return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		UserResponse productResp = prodServiceImpl.getProduct(requestBody);
		return new ResponseEntity<>(productResp, HttpStatus.OK);
		}
		
		 catch (Exception e) {
				logger.error("Exception caught : " + e.getMessage());
				errorResponse.setStatus(FAIL);
				errorResponse.setMessage("Inavid Product Id!");
				return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);

			}
		}
	
}
