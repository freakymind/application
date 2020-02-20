package com.dsc.serviceimpl;

import static com.dsc.constants.CompanyConstants.COMPANYUSER_SUCCESS;
import static com.dsc.constants.CompanyConstants.COMPANYUSER_UPDATED_SUCCESS;
import static com.dsc.constants.CompanyConstants.FAIL;
import static com.dsc.constants.CompanyConstants.SUCCESS;
import static com.dsc.constants.CompanyConstants.USER_EXISTS;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.dsc.dao.RegisterCompnayDao;
import com.dsc.handler.RegisterCompanyHandler;
import com.dsc.mapper.CompanyMapper;
import com.dsc.model.RegisterCompany;
import com.dsc.model.RegisterCompany.Company;
import com.dsc.model.RegisterCompany.User;
import com.dsc.response.UserResponse;
import com.dsc.service.CompanyUserService;

@Service
public class CompanyUserServiceImpl implements CompanyUserService {

	@Autowired
	private RegisterCompnayDao regCompdao;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	UserResponse response = new UserResponse();
	private static final Logger logger = LoggerFactory.getLogger(CompanyUserServiceImpl.class);

	@Override
	public UserResponse registerCompanyUser(RegisterCompanyHandler requestBody) throws Exception {
		logger.debug("Incoming request : " + requestBody);
		RegisterCompany allCompanyDetails = CompanyMapper.mapAllCompanyDetails(requestBody);
		List<RegisterCompany> findAll = regCompdao.findAll();

		for (RegisterCompany compRef : findAll) {
			String companyRef = compRef.getCompany().getCompany_ref();

			if (requestBody.getCompany_ref().equals(companyRef)) {

				ArrayList<User> list2 = compRef.getUser();
				Company company = compRef.getCompany();

				ArrayList<User> user = allCompanyDetails.getUser();
				Date date = new Date();
				RegisterCompany details = null;
				details = regCompdao.findByUserEmail(requestBody.getUser_email());
				RegisterCompany findByCompany_ref = regCompdao.findByCompanycompany_ref(requestBody.getCompany_ref());

				if (details == null) {
					if (requestBody.getUser_password() == null) {
						user.get(0).setPassword("Ojas1525");
					}
					String encode = passwordEncoder.encode(user.get(0).getPassword());
					user.get(0).setPassword(encode);
					user.get(0).setRole("COMPANY_USER");
					user.get(0).setUser_status(true);
					user.get(0).setCreated_on(date);
					list2.addAll(user);

					String id = findByCompany_ref.getId();
					logger.info("Id is :" + id);
					allCompanyDetails.setUser(list2);
					allCompanyDetails.setId(id);
					allCompanyDetails.setCompany(company);
					regCompdao.save(allCompanyDetails);
					logger.info("Company User added successfully!");
					response.setData(user);
					response.setMessage(COMPANYUSER_SUCCESS);
					response.setStatus(SUCCESS);
					return response;

				}
			}
		}
		logger.error("Failed to process request");
		response.setStatus(FAIL);
		response.setMessage(USER_EXISTS);
		return response;
	}

	@Override
	public UserResponse updateCompanyUser(RegisterCompanyHandler requestBody) throws Exception {
		logger.debug("Incoming update request : " + requestBody);
		RegisterCompany allCompanyDetails = CompanyMapper.mapAllCompanyDetails(requestBody);

		ArrayList<User> user = allCompanyDetails.getUser();
		Date date = new Date();

		RegisterCompany findByUserEmailObj = regCompdao.findByUserEmail(requestBody.getUser_email());
		Company company = findByUserEmailObj.getCompany();
		ArrayList<User> userarrayList = findByUserEmailObj.getUser();

		for (User userdata : userarrayList) {
			String email1 = userdata.getEmail();
			String role = userdata.getRole();
			Date created_on = userdata.getCreated_on();

			if (email1.equals(requestBody.getUser_email()) && role.equals(requestBody.getUser_role())) {
				String encode = passwordEncoder.encode(user.get(0).getPassword());
				userdata.setPassword(encode);
				userdata.setRole(role);
				userdata.setUser_status(true);
				userdata.setCreated_on(created_on);
				userdata.setUpdated_on(date);
				userdata.setEmail(email1);
				userdata.setUser_address(requestBody.getUser_address());
				userdata.setUser_country(requestBody.getUser_country());
				userdata.setUser_mobile(requestBody.getUser_mobile());
				userdata.setUser_name(requestBody.getUser_name());
				userarrayList.add(userdata);
				String id = findByUserEmailObj.getId();
				logger.info("Id is :" + id);
				allCompanyDetails.setId(id);
				allCompanyDetails.setUser(userarrayList);
				allCompanyDetails.setCompany(company);
				regCompdao.save(allCompanyDetails);
				logger.info("User details updated successfully!");
				response.setData(user);
				response.setMessage(COMPANYUSER_UPDATED_SUCCESS);
				response.setStatus(SUCCESS);
				return response;
			}

		}
		logger.error("Failed to process update request");
		response.setStatus(FAIL);
		response.setMessage(USER_EXISTS);
		return response;
	}
}
