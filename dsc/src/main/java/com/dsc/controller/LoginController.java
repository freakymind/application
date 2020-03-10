package com.dsc.controller;

import static com.dsc.constants.CompanyConstants.FAIL;
import static com.dsc.constants.CompanyConstants.REQUEST_EMPTY;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dsc.dao.RegisterCompnayDao;
import com.dsc.dao.UserDao;
import com.dsc.handler.LoginHandler;
import com.dsc.response.ErrorResponse;
import com.dsc.security.auth.configs.JwtTokenProvider;

@RestController
@RequestMapping("/service")
@CrossOrigin(origins = "*", maxAge = 3600)
public class LoginController {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	JwtTokenProvider jwtTokenProvider;
	
	@Autowired
	private UserDao userDao;


	private static final Logger logger =  LoggerFactory.getLogger(LoginController.class);
	ErrorResponse errorResponse = new ErrorResponse();

	@PostMapping("/login")
	public ResponseEntity<Object> userLogin(@RequestBody LoginHandler login) {
		logger.debug("Incoming request : " + login);

		try {

			if (login == null) {
				logger.error("Invalid request");
				errorResponse.setStatus(FAIL);
				errorResponse.setMessage("Invalid Request");
				return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
			}

			if ((login.getEmail().isEmpty() || login.getEmail() == null)
					|| (login.getPassword().isEmpty() || login.getPassword() == null)) {
				logger.error("Data must not be null");
				errorResponse.setMessage(REQUEST_EMPTY);
				errorResponse.setStatus(FAIL);
				errorResponse.setData(null);
				return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			String email = login.getEmail();
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, login.getPassword()));
			String token = jwtTokenProvider.createToken(email,
					userDao.findByUserEmail(email).getUserdetails().get(0).getRole());
			Map<Object, Object> model = new HashMap<>();
			model.put("Email", email);
			model.put("Token", "Bearer " + token);
			return new ResponseEntity<>(model, HttpStatus.OK);
		} catch (AuthenticationException e) {
			logger.error("Invalid email/password");
			errorResponse.setStatus(FAIL);
			errorResponse.setData(null);
			throw new BadCredentialsException("Invalid email/password !");
		}

	}

}
