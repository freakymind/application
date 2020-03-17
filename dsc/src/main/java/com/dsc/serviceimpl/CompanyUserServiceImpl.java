package com.dsc.serviceimpl;

import static com.dsc.constants.CompanyConstants.COMPANYUSER_SUCCESS;
import static com.dsc.constants.CompanyConstants.COMPANYUSER_UPDATED_SUCCESS;
import static com.dsc.constants.CompanyConstants.USER_DELETED_SUCCESS;
import static com.dsc.constants.CompanyConstants.FAIL;
import static com.dsc.constants.CompanyConstants.SUCCESS;
import static com.dsc.constants.CompanyConstants.USER_EXISTS;
import static com.dsc.constants.CompanyConstants.USER_ID;
import static com.dsc.constants.CompanyConstants.USER_UPDATE_FAILED;
import static com.dsc.constants.CompanyConstants.USER_DELETE_FAILED;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.dsc.dao.RegisterCompnayDao;
import com.dsc.dao.UserDao;
import com.dsc.handler.RegisterCompanyHandler;
import com.dsc.mapper.CompanyMapper;
import com.dsc.mapper.UserMapper;
import com.dsc.model.RegisterCompany;
import com.dsc.model.RegisterCompany.Company;
import com.dsc.model.RegisterCompany.Distributor;
import com.dsc.model.RegisterCompany.User;
import com.dsc.model.User.UserDetails;
import com.dsc.response.UserResponse;
import com.dsc.service.CompanyUserService;

@Service
public class CompanyUserServiceImpl implements CompanyUserService {

	@Autowired
	private RegisterCompnayDao regCompdao;

	@Autowired
	private RegisterCompanyServiceImpl regServiceImpl;

	@Autowired
	private UserDao userdao;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	UserResponse response = new UserResponse();
	private static final Logger logger = LoggerFactory.getLogger(CompanyUserServiceImpl.class);

	@Override
	public UserResponse registerCompanyUser(RegisterCompanyHandler requestBody) throws Exception {
		logger.debug("Incoming request : " + requestBody);
		RegisterCompany allCompanyDetails = CompanyMapper.mapAllCompanyDetails(requestBody);
		com.dsc.model.User allUserDetails = UserMapper.mapAllUserDetails(requestBody);
		List<RegisterCompany> findAllCompanys = regCompdao.findAll();
		List<com.dsc.model.User> findAllUsers = userdao.findAll();
		String generatedUserId = USER_ID;

		for (RegisterCompany compRef : findAllCompanys) {
			for (com.dsc.model.User users : findAllUsers) {
				String companyRef = compRef.getCompany().getCompany_ref();
				String user_id = users.getUserdetails().get(0).getUser_id();
				if (requestBody.getCompany_ref().equals(companyRef) && requestBody.getUser_id().equals(user_id)) {

					ArrayList<User> usersIds = allCompanyDetails.getUserid();
					ArrayList<UserDetails> userdetails = allUserDetails.getUserdetails();

					Company company = compRef.getCompany();
					ArrayList<User> compUserIdList = compRef.getUserid();
					ArrayList<UserDetails> userdetailsList = users.getUserdetails();

					Date date = new Date();
					com.dsc.model.User details = null;
					details = userdao.findByUserEmail(requestBody.getUser_email());
					RegisterCompany findByCompany_ref = regCompdao
							.findByCompanycompany_ref(requestBody.getCompany_ref());
					com.dsc.model.User findObjId = userdao.findByUserEmail(users.getUserdetails().get(0).getEmail());

					if (details == null) {
						if (requestBody.getUser_password() == null) {
							userdetails.get(0).setPassword("Ojas1525");
						}
						String encode = passwordEncoder.encode(userdetails.get(0).getPassword());
						generatedUserId += regServiceImpl.generatedRefIds();
						userdetails.get(0).setUser_id(generatedUserId);
						userdetails.get(0).setPassword(encode);
						userdetails.get(0).setRole("COMPANY_USER");
						userdetails.get(0).setUser_status("PENDING");
						userdetails.get(0).setCreated_on(date);
						userdetailsList.addAll(userdetails);

						usersIds.get(0).setUser_id(generatedUserId);
						compUserIdList.addAll(usersIds);

						String compObjId = findByCompany_ref.getId();
						logger.info("CompObjId is :" + compObjId);
						allCompanyDetails.setId(compObjId);
						allCompanyDetails.setCompany(company);
						allCompanyDetails.setUserid(compUserIdList);

						String userObjId = findObjId.getId();
						logger.info("UserObjId is :" + userObjId);
						allUserDetails.setId(userObjId);
						allUserDetails.setUserdetails(userdetailsList);

						allCompanyDetails.getDistributor().clear();
						regCompdao.save(allCompanyDetails);
						userdao.save(allUserDetails);
						logger.info("Company User added successfully!");
						response.setData(userdetails);
						response.setMessage(COMPANYUSER_SUCCESS);
						response.setStatus(SUCCESS);
						return response;
					}
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
		com.dsc.model.User mapAllUserDetails = UserMapper.mapAllUserDetails(requestBody);
		Date date = new Date();
		com.dsc.model.User findByUserEmailObj = userdao.findByUserEmail(requestBody.getUser_email());
		ArrayList<UserDetails> userarrayList = findByUserEmailObj.getUserdetails();
		int i = 0;
		for (UserDetails userdata : userarrayList) {
			String email1 = userdata.getEmail();
			String role = userdata.getRole();
			Date created_on = userdata.getCreated_on();
			if (email1.equals(requestBody.getUser_email())) {
				String encode = passwordEncoder.encode(userdata.getPassword());
				userdata.setPassword(encode);
				userdata.setRole(role);
				userdata.setUser_status("PENDING");
				userdata.setCreated_on(created_on);
				userdata.setUpdated_on(date);
				userdata.setEmail(email1);
				userdata.setUser_address(requestBody.getUser_address());
				userdata.setUser_country(requestBody.getUser_country());
				userdata.setUser_mobile(requestBody.getUser_mobile());
				userdata.setUser_name(requestBody.getUser_name());
				userarrayList.set(i, userdata);
				String id = findByUserEmailObj.getId();
				logger.info("Id is :" + id);
				mapAllUserDetails.setId(id);
				mapAllUserDetails.setUserdetails(userarrayList);
				userdao.save(mapAllUserDetails);
				logger.info("User details updated successfully!");
				response.setData(userdata);
				response.setMessage(COMPANYUSER_UPDATED_SUCCESS);
				response.setStatus(SUCCESS);
				return response;
			}
			i++;
		}
		logger.error("Failed to process update request");
		response.setStatus(FAIL);
		response.setMessage(USER_UPDATE_FAILED);
		return response;
	}

	@Override
	public UserResponse userStatusUpdate(RegisterCompanyHandler requestBody) throws Exception {
		logger.debug("Incoming delete request : " + requestBody);
		com.dsc.model.User mapAllUserDetails = UserMapper.mapAllUserDetails(requestBody);
		RegisterCompany mapAllCompanyDetails = CompanyMapper.mapAllCompanyDetails(requestBody);
		Date date = new Date();
		com.dsc.model.User findByUserEmailObj = userdao.findByUserEmail(requestBody.getUser_email());
		String role2 = findByUserEmailObj.getUserdetails().get(0).getRole();
		ArrayList<UserDetails> userarrayList = findByUserEmailObj.getUserdetails();
		RegisterCompany adminIds = regCompdao.findByCompanycomp_admin_id(requestBody.getUser_id());
		ArrayList<User> userIdsList = adminIds.getUserid();
		ArrayList<Distributor> distIdsList = adminIds.getDistributor();
		int i = 0;
		for (UserDetails userdata : userarrayList) {
			String userId2 = userdata.getUser_id();
			String email1 = userdata.getEmail();
			String role = userdata.getRole();
			if (email1.equals(requestBody.getUser_email()) && role2.equals("COMPANY_ADMIN")) {
				userdata.setUser_status(requestBody.getUser_status());
				userdata.setUpdated_on(date);
				userarrayList.set(i, userdata);
				String id = findByUserEmailObj.getId();
				logger.info("Id is :" + id);
				mapAllUserDetails.setId(id);
				mapAllUserDetails.setUserdetails(userarrayList);
				userdao.save(mapAllUserDetails);
				if (role.equals("COMPANY_USER")) {
					int j = 0;
					for (User userIdlist : userIdsList) {
						String userid3 = userIdlist.getUser_id();
						if (userid3.equals(userId2)) {
							adminIds.getUserid().remove(j);
							regCompdao.save(adminIds);
							break;
						}
						j++;
					}
				} else if (role.equals("DISTRIBUTOR")) {
					int k = 0;
					for (Distributor distIdlist : distIdsList) {
						String distid3 = distIdlist.getDist_id();
						if (distid3.equals(userId2)) {
							adminIds.getDistributor().remove(k);
							regCompdao.save(adminIds);
							break;
						}
						k++;
					}
				}
				logger.info("User details deleted successfully!");
				response.setData(userdata);
				response.setMessage(USER_DELETED_SUCCESS);
				response.setStatus(SUCCESS);
				return response;
			}
			i++;
		}

		logger.error("Failed to process delete request");
		response.setStatus(FAIL);
		response.setMessage(USER_DELETE_FAILED);
		return response;

	}
}
