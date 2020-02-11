package com.dsc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dsc.handler.RegisterCompanyHandler;
import com.dsc.response.ErrorResponse;
import com.dsc.response.UserResponse;
import com.dsc.serviceimpl.RegisterCompanyServiceImpl;

@RestController
@RequestMapping("/service")
@CrossOrigin(origins = "*", maxAge = 3600)
public class RegisterCompanyController {

	@Autowired
	RegisterCompanyServiceImpl regCompService;

	@Autowired
	AuthenticationManager authenticationManager;

	public static Integer FAIL = 1;
	public static String REQUESTEMPTY = "Please Enter All the required Details";
	ErrorResponse errorResponse = new ErrorResponse();

//	@Secured({ "SUPER_ADMIN", "COMPANY_ADMIN" })
	@PostMapping("/registercompany")
	public ResponseEntity<Object> registerCompany(@RequestBody RegisterCompanyHandler requestBody,
			HttpServletRequest request, HttpServletResponse response) {

		try {

			if (requestBody == null) {
				ErrorResponse error = new ErrorResponse();
				error.setMessage("Invalid Request");
				return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
			}

			if ((requestBody.getCompany_name().isEmpty() || requestBody.getCompany_name() == null)
					|| (requestBody.getCompany_address().isEmpty() || requestBody.getCompany_address() == null)
					|| (requestBody.getCompany_email().isEmpty() || requestBody.getCompany_email() == null)
					|| (requestBody.getCompany_mobile().isEmpty() || requestBody.getCompany_mobile() == null)
					|| (requestBody.getEmail().isEmpty() || requestBody.getEmail() == null)
					|| (requestBody.getAddress().isEmpty() || requestBody.getAddress() == null)
					|| (requestBody.getFullname().isEmpty() || requestBody.getFullname() == null)
					|| (requestBody.getMobile().isEmpty() || requestBody.getMobile() == null)
					|| (requestBody.getCountry().isEmpty() || requestBody.getCountry() == null)) {
				errorResponse.setMessage(REQUESTEMPTY);
				errorResponse.setStatus(FAIL);
				errorResponse.setData(null);
				return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			UserResponse userResponse = regCompService.registerCompany(requestBody);
			return new ResponseEntity<>(userResponse, HttpStatus.OK);

		} catch (Exception e) {
			ErrorResponse error = new ErrorResponse();
			error.setMessage("Exception caught Register Compnay controller!");
			return new ResponseEntity<>(error, HttpStatus.CONFLICT);

		}
	}

}
