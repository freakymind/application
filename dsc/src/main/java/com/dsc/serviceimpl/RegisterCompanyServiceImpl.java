package com.dsc.serviceimpl;

import static com.dsc.constants.CompanyConstants.FAIL;
import static com.dsc.constants.CompanyConstants.REGISTER_SUCCESS;
import static com.dsc.constants.CompanyConstants.SUCCESS;
import static com.dsc.constants.CompanyConstants.USER_EXISTS;
import static com.dsc.constants.CompanyConstants.NO_RECORDS;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	@Autowired
	private RegisterCompnayDao regCompdao;

	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private Environment env;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	UserResponse response = new UserResponse();
	private static final Logger logger = LoggerFactory.getLogger(RegisterCompanyServiceImpl.class);

	@Override
	public UserResponse registerCompany(RegisterCompanyHandler requestBody) throws Exception {
		logger.debug("Incoming request : " + requestBody);

		RegisterCompany mappedDetails = CompanyMapper.mapAllCompanyDetails(requestBody);
		Date date = new Date();
		RegisterCompany details = null;
		details = regCompdao.findByUserEmail(requestBody.getUser_email());
		if (details == null) {
			if (requestBody.getUser_password() == null) {
				mappedDetails.getUser().get(0).setPassword("Ojas1525");
			}
			String encode = passwordEncoder.encode(mappedDetails.getUser().get(0).getPassword());
			mappedDetails.getUser().get(0).setPassword(encode);
			String generatedCompanyRef = generatecompanyReference();
			mappedDetails.getUser().get(0).setRole("COMPANY_ADMIN");
			mappedDetails.getUser().get(0).setUser_status(true);
			mappedDetails.getUser().get(0).setCreated_on(date);
			mappedDetails.getCompany().setCompany_ref(generatedCompanyRef);
			mappedDetails.getCompany().setCompany_status(false);
			mappedDetails.getCompany().setCreated_on(date);
			regCompdao.save(mappedDetails);
			String email = requestBody.getUser_email();
			sendEmail(email);
			logger.info("Company registration successfully!");
			response.setData(mappedDetails);
			response.setMessage(REGISTER_SUCCESS);
			response.setStatus(SUCCESS);
			return response;

		}
		logger.error("Failed to process request");
		response.setStatus(FAIL);
		response.setMessage(USER_EXISTS);
		return response;
	}

	public String generatecompanyReference() {
		Random random = new Random();
		String string = Long.toString(random.nextLong() & Long.MAX_VALUE, 24);
		return string;

	}

	public Boolean sendEmail(String email) throws MailException {
		Boolean sent = false;
		logger.debug("user email : " + email);
		try {
			if (email != null) {
				SimpleMailMessage mailMessage = new SimpleMailMessage();
				mailMessage.setFrom(env.getProperty("spring.mail.username"));
				mailMessage.setTo(email);
				mailMessage.setSubject("Hi your Registration successfully!");
				mailMessage.setText("Welcome to Ojas");
				javaMailSender.send(mailMessage);
				logger.info("Email sent successfully!");
				sent = true;
			}
			return sent;
		} catch (MailException e) {
			logger.error("Failed to email sent");
			e.printStackTrace();
			throw new MailAuthenticationException("Invalid Credentials !");
		}
	}

	@Override
	public UserResponse getregisterCompany() throws Exception {
		List<RegisterCompany> findAll = regCompdao.findAll();

		if (findAll.isEmpty()) {
			logger.info("No records found !");
			response.setMessage(NO_RECORDS);
			response.setData(findAll);
			response.setStatus(FAIL);
			return response;

		}
		logger.info("Get all Company details successfully!");
		response.setData(findAll);
		response.setStatus(SUCCESS);
		return response;

	}
}
