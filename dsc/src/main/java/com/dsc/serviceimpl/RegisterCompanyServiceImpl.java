package com.dsc.serviceimpl;

import static com.dsc.constants.CompanyConstants.COMP_REF_ID;
import static com.dsc.constants.CompanyConstants.FAIL;
import static com.dsc.constants.CompanyConstants.NO_RECORDS;
import static com.dsc.constants.CompanyConstants.REGISTER_SUCCESS;
import static com.dsc.constants.CompanyConstants.SUCCESS;
import static com.dsc.constants.CompanyConstants.USER_EXISTS;
import static com.dsc.constants.CompanyConstants.USER_ID;

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
import com.dsc.dao.UserDao;
import com.dsc.handler.RegisterCompanyHandler;
import com.dsc.mapper.CompanyMapper;
import com.dsc.mapper.UserMapper;
import com.dsc.model.RegisterCompany;
import com.dsc.model.User;
import com.dsc.response.UserResponse;
import com.dsc.service.RegisterCompanyService;

@Service
public class RegisterCompanyServiceImpl implements RegisterCompanyService {

	@Autowired
	private RegisterCompnayDao regCompdao;

	@Autowired
	private UserDao userdao;

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
		User mapUserDetails = UserMapper.mapAllUserDetails(requestBody);
		Date date = new Date();
		User details = null;
		RegisterCompany details1 = null;
		String generatedCompanyRef = COMP_REF_ID;
		String generatedUserId = USER_ID;
		details = userdao.findByUserEmail(requestBody.getUser_email());
		details1 = regCompdao.findByCompanyEmail(requestBody.getCompany_email());
		if (details == null && details1 == null) {
			if (requestBody.getUser_password() == null) {
				mapUserDetails.getUserdetails().get(0).setPassword("Ojas1525");
			}
			String encode = passwordEncoder.encode(mapUserDetails.getUserdetails().get(0).getPassword());
			mapUserDetails.getUserdetails().get(0).setPassword(encode);
			generatedCompanyRef += generatedRefIds();
			generatedUserId += generatedRefIds();
			mappedDetails.getCompany().setComp_admin_id(generatedUserId);
			mappedDetails.getCompany().setCompany_ref(generatedCompanyRef);
			mappedDetails.getCompany().setCompany_status(false);
			mappedDetails.getCompany().setCreated_on(date);
			mappedDetails.getDistributor().clear();
			mappedDetails.getProduct().clear();
			mappedDetails.getUserid().get(0).setUser_id(generatedUserId);
			mapUserDetails.getUserdetails().get(0).setUser_id(generatedUserId);
			mapUserDetails.getUserdetails().get(0).setRole("COMPANY_ADMIN");
			mapUserDetails.getUserdetails().get(0).setUser_status("PENDING");
			mapUserDetails.getUserdetails().get(0).setCreated_on(date);

			regCompdao.save(mappedDetails);
			userdao.save(mapUserDetails);
			String email = requestBody.getUser_email();
//			sendEmail(email);
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

	public String generatedRefIds() {
		Random ran = new Random();
		String b = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String sc = "0123456789";
		String random = "";
		for (int j = 0; j < 4; j++) {
			int sz = ran.nextInt(sc.length());
			random = random + sc.charAt(sz);
		}
		for (int i = 0; i < 2; i++) {
			int a = ran.nextInt(b.length());
			random = random + b.charAt(a);
		}
		System.out.println("Num Gen : " + random);
		return random;
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

}
