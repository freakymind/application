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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dsc.handler.RegisterCompanyHandler;
import com.dsc.response.ErrorResponse;
import com.dsc.response.UserResponse;
import com.dsc.serviceimpl.CompanyUserServiceImpl;

@RestController
@RequestMapping("/service/companyusers/**")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CompanyUserController {

	@Autowired
	CompanyUserServiceImpl compuserService;

	private static final Logger logger = LoggerFactory.getLogger(CompanyUserController.class);
	ErrorResponse errorResponse = new ErrorResponse();

	@Secured({ "COMPANY_ADMIN" })
	@PutMapping("/addcompanyuser")
	public ResponseEntity<Object> registerCompanyUser(@RequestBody RegisterCompanyHandler requestBody,
			HttpServletRequest request, HttpServletResponse response) {
		logger.debug("Incoming request : " + requestBody);

		try {

			if (requestBody == null) {
				logger.error("Invalid request");
				errorResponse.setStatus(FAIL);
				errorResponse.setMessage("Invalid Request");
				return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
			}

			if ((requestBody.getUser_email().isEmpty() || requestBody.getUser_email() == null)
					|| (requestBody.getUser_address().isEmpty() || requestBody.getUser_address() == null)
					|| (requestBody.getUser_name().isEmpty() || requestBody.getUser_name() == null)
					|| (requestBody.getUser_mobile().isEmpty() || requestBody.getUser_mobile() == null)
					|| (requestBody.getUser_country().isEmpty() || requestBody.getUser_country() == null)
					|| (requestBody.getUser_id().isEmpty() || requestBody.getUser_id() == null)
					|| (requestBody.getCompany_ref().isEmpty() || requestBody.getCompany_ref() == null)) {
				logger.error("Data must not be null");
				errorResponse.setMessage(REQUEST_EMPTY);
				errorResponse.setStatus(FAIL);
				errorResponse.setData(null);
				return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			UserResponse companyUserResp = compuserService.registerCompanyUser(requestBody);
			return new ResponseEntity<>(companyUserResp, HttpStatus.OK);

		} catch (Exception e) {
			logger.error("Exception caught : " + e.getMessage());
			errorResponse.setStatus(FAIL);
			errorResponse.setMessage("Exception caught Compnay User controller!");
			return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);

		}
	}

	@Secured({ "COMPANY_ADMIN" })
	@PatchMapping("/update_user")
//	@PutMapping("/update_user")
	public ResponseEntity<Object> UpdateCompanyUser(@RequestBody RegisterCompanyHandler requestBody,
			HttpServletRequest request, HttpServletResponse response) {
		logger.debug("Incoming request : " + requestBody);

		try {

			if (requestBody == null) {
				logger.error("Invalid request");
				errorResponse.setStatus(FAIL);
				errorResponse.setMessage("Invalid Request");
				return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
			}

			if ((requestBody.getUser_email().isEmpty() || requestBody.getUser_email() == null)
					|| (requestBody.getUser_address().isEmpty() || requestBody.getUser_address() == null)
					|| (requestBody.getUser_name().isEmpty() || requestBody.getUser_name() == null)
					|| (requestBody.getUser_mobile().isEmpty() || requestBody.getUser_mobile() == null)
					|| (requestBody.getUser_country().isEmpty() || requestBody.getUser_country() == null)) {
				logger.error("Data must not be null");
				errorResponse.setMessage(REQUEST_EMPTY);
				errorResponse.setStatus(FAIL);
				errorResponse.setData(null);
				return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			UserResponse companyUserResp = compuserService.updateCompanyUser(requestBody);
			return new ResponseEntity<>(companyUserResp, HttpStatus.OK);

		} catch (Exception e) {
			logger.error("Exception caught : " + e.getMessage());
			errorResponse.setStatus(FAIL);
			errorResponse.setMessage("Exception caught Compnay User update controller!");
			return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);

		}
	}

}
