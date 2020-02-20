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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dsc.handler.RegisterCompanyHandler;
import com.dsc.response.ErrorResponse;
import com.dsc.response.UserResponse;
import com.dsc.serviceimpl.RegisterCompanyServiceImpl;

@RestController
@RequestMapping("/service/**")
@CrossOrigin(origins = "*", maxAge = 3600)
public class RegisterCompanyController {

	@Autowired
	RegisterCompanyServiceImpl regCompService;

	@Autowired
	AuthenticationManager authenticationManager;

	private static final Logger logger = LoggerFactory.getLogger(RegisterCompanyController.class);
	ErrorResponse errorResponse = new ErrorResponse();

	@PostMapping("/registercompany")
	public ResponseEntity<Object> registerCompany(@RequestBody RegisterCompanyHandler requestBody,
			HttpServletRequest request, HttpServletResponse response) {
		logger.debug("Incoming request : " + requestBody);

		try {

			if (requestBody == null) {
				logger.error("Invalid request");
				errorResponse.setStatus(FAIL);
				errorResponse.setMessage("Invalid Request");
				return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
			}

			if ((requestBody.getCompany_name().isEmpty() || requestBody.getCompany_name() == null)
					|| (requestBody.getCompany_address().isEmpty() || requestBody.getCompany_address() == null)
					|| (requestBody.getCompany_email().isEmpty() || requestBody.getCompany_email() == null)
					|| (requestBody.getCompany_mobile().isEmpty() || requestBody.getCompany_mobile() == null)
					|| (requestBody.getUser_email().isEmpty() || requestBody.getUser_email() == null)
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
			UserResponse userResponse = regCompService.registerCompany(requestBody);
			return new ResponseEntity<>(userResponse, HttpStatus.OK);

		} catch (Exception e) {
			logger.error("Exception caught : " + e.getMessage());
			errorResponse.setStatus(FAIL);
			errorResponse.setMessage("Exception caught Register Compnay controller!");
			return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);

		}
	}

	@GetMapping("/getall")
	public ResponseEntity<Object> getRegisterCompany(HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("Incoming request : " );

		UserResponse userResponse = regCompService.getregisterCompany();
		return new ResponseEntity<>(userResponse, HttpStatus.OK);
	}
}
