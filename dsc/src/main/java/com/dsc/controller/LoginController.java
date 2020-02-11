package com.dsc.controller;

import java.util.HashMap;
import java.util.Map;

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
	private RegisterCompnayDao regDao;

	@PostMapping("/login")
	public ResponseEntity<Object> userLogin(@RequestBody LoginHandler login) {

		try {

			if (login == null || (login.getEmail().isEmpty() || login.getEmail() == null)
					|| (login.getPassword().isEmpty() || login.getPassword() == null)) {
				ErrorResponse error = new ErrorResponse();
				error.setMessage("Invalid Request");
				return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			String email = login.getEmail();
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, login.getPassword()));
			String token = jwtTokenProvider.createToken(email,
					regDao.findByUserEmail(email).getUser().get(0).getRole());
			Map<Object, Object> model = new HashMap<>();
			model.put("Email", email);
			model.put("Token", "Bearer " + token);
			return new ResponseEntity<>(model, HttpStatus.OK);
		} catch (AuthenticationException e) {
			throw new BadCredentialsException("Invalid email/password !");
		}

	}

}
