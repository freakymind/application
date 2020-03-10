package com.dsc.serviceimpl;

import static com.dsc.constants.CompanyConstants.DISTRIBUTOR_SUCCESS;
import static com.dsc.constants.CompanyConstants.FAIL;
import static com.dsc.constants.CompanyConstants.SUCCESS;
import static com.dsc.constants.CompanyConstants.USER_EXISTS;
import static com.dsc.constants.CompanyConstants.DIST_ID;

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
import com.dsc.service.DistributorService;

@Service
public class DistributorServiceImpl implements DistributorService {

	@Autowired
	private RegisterCompnayDao regCompdao;
	
	@Autowired
	private UserDao userdao;
	
	@Autowired
	private RegisterCompanyServiceImpl regServiceImpl;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	UserResponse response = new UserResponse();
	private static final Logger logger = LoggerFactory.getLogger(DistributorServiceImpl.class);

	@Override
	public UserResponse registerDistributor(RegisterCompanyHandler requestBody) throws Exception {
		logger.debug("Incoming request : " + requestBody);
		RegisterCompany allCompanyDetails = CompanyMapper.mapAllCompanyDetails(requestBody);
		com.dsc.model.User allUserDetails = UserMapper.mapAllUserDetails(requestBody);
		List<RegisterCompany> findAllCompanys = regCompdao.findAll();
		List<com.dsc.model.User> findAllUsers = userdao.findAll();
		String generatedDistId = DIST_ID;

		for (RegisterCompany compRef : findAllCompanys) {
			for (com.dsc.model.User users : findAllUsers) {
				String companyRef = compRef.getCompany().getCompany_ref();
				String user_id = users.getUserdetails().get(0).getUser_id();
				if (requestBody.getCompany_ref().equals(companyRef) && requestBody.getUser_id().equals(user_id)) {

					ArrayList<User> usersIds = allCompanyDetails.getUserid();
					ArrayList<Distributor> distIds = allCompanyDetails.getDistributor();
					ArrayList<UserDetails> userdetails = allUserDetails.getUserdetails();

					Company company = compRef.getCompany();
					ArrayList<User> compUserIdList = compRef.getUserid();
					ArrayList<Distributor> distIdList = compRef.getDistributor();
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
						generatedDistId += regServiceImpl.generatedRefIds();
						userdetails.get(0).setUser_id(generatedDistId);
						userdetails.get(0).setPassword(encode);
						userdetails.get(0).setRole("DISTRIBUTOR");
						userdetails.get(0).setUser_status("PENDING");
						userdetails.get(0).setCreated_on(date);
						userdetailsList.addAll(userdetails);

						distIds.get(0).setDist_id(generatedDistId);
						distIdList.addAll(distIds);

						String compObjId = findByCompany_ref.getId();
						logger.info("CompObjId is :" + compObjId);
						allCompanyDetails.setId(compObjId);
						allCompanyDetails.setCompany(company);
						allCompanyDetails.setUserid(compUserIdList);
						allCompanyDetails.setDistributor(distIdList);

						String userObjId = findObjId.getId();
						logger.info("UserObjId is :" + userObjId);
						allUserDetails.setId(userObjId);
						allUserDetails.setUserdetails(userdetailsList);

						regCompdao.save(allCompanyDetails);
						userdao.save(allUserDetails);
						logger.info("Company Distributor added successfully!");
						response.setData(userdetails);
						response.setMessage(DISTRIBUTOR_SUCCESS);
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

}
