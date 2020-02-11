package com.dsc.serviceimpl;

import java.security.SecureRandom;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.dsc.dao.RegisterCompnayDao;
import com.dsc.handler.RegisterCompanyHandler;
import com.dsc.mapper.CompanyMapper;
import com.dsc.model.RegisterCompany;
import com.dsc.response.UserResponse;
import com.dsc.service.RegisterCompanyService;

@Service
public class RegisterCompanyServiceImpl implements RegisterCompanyService {

	public static Integer SUCCESS = 0;
	public static String REGISTERSUCCESS = "Company Registration Success.Please wait for the confirmation mail.";
	public static String USEREXISTS = "User Already Exists with the email";
	static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	static SecureRandom rnd = new SecureRandom();

	@Autowired
	private RegisterCompnayDao regCompdao;

	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private Environment env;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	UserResponse response = new UserResponse();

	@Override
	public UserResponse registerCompany(RegisterCompanyHandler requestBody) throws Exception {

		RegisterCompany mappedDetails = CompanyMapper.mapAllCompanyDetails(requestBody);
		RegisterCompany details = null;
		details = regCompdao.findByUserEmail(requestBody.getEmail());
		if (details == null) {
			if (requestBody.getPassword() == null) {
				mappedDetails.getUser().get(0).setPassword("Ojas1525");
			}
			String encode = passwordEncoder.encode(mappedDetails.getUser().get(0).getPassword());
			mappedDetails.getUser().get(0).setPassword(encode);
			String generatedCompanyRef = generatecompanyReference();
			mappedDetails.getUser().get(0).setCompanyRef(generatedCompanyRef);
			mappedDetails.getUser().get(0).setRole("COMPANY_ADMIN");
			mappedDetails.getUser().get(0).setActive(false);
			mappedDetails.getCompany().setCompanyRef(generatedCompanyRef);
			mappedDetails.getCompany().setStatus(false);
			regCompdao.save(mappedDetails);
			String email = requestBody.getEmail();
			sendEmail(email);

			response.setData(mappedDetails);
			response.setMessage(REGISTERSUCCESS);
			response.setStatus(SUCCESS);
			return response;

		}
		response.setStatus(SUCCESS);
		response.setMessage(USEREXISTS);
		return response;
	}

	public String generatecompanyReference() {
		Random random = new Random();
		String string = Long.toString(random.nextLong() & Long.MAX_VALUE, 24);
		return string;

	}

	public Boolean sendEmail(String email) throws MailException {
		Boolean sent = false;

		try {
			if (email != null) {

				SimpleMailMessage mailMessage = new SimpleMailMessage();
				mailMessage.setFrom(env.getProperty("spring.mail.username"));
				mailMessage.setTo(email);
				mailMessage.setSubject("Hi Registration Success");
				mailMessage.setText("Welcome to Ojas");
				javaMailSender.send(mailMessage);
				sent = true;
			}
			return sent;
		} catch (MailException e) {
			e.printStackTrace();
			throw new MailAuthenticationException("Invalid Credentials !");
		}
	}
}
