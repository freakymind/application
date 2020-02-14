package com.dsc.serviceimpl;

import static com.dsc.constants.CompanyConstants.DISTRIBUTOR_SUCCESS;
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
import com.dsc.model.RegisterCompany.Distributor;
import com.dsc.model.RegisterCompany.User;
import com.dsc.response.UserResponse;
import com.dsc.service.DistributorService;

@Service
public class DistributorServiceImpl implements DistributorService {

	@Autowired
	private RegisterCompnayDao regCompdao;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	UserResponse response = new UserResponse();
	private static final Logger logger = LoggerFactory.getLogger(DistributorServiceImpl.class);

	@Override
	public UserResponse registerDistributor(RegisterCompanyHandler requestBody) throws Exception {
		logger.debug("Incoming request : " + requestBody);
		RegisterCompany allCompanyDetails = CompanyMapper.mapAllCompanyDetails(requestBody);
		List<RegisterCompany> findAll = regCompdao.findAll();

		for (RegisterCompany compRef : findAll) {
			String companyRef = compRef.getCompany().getCompany_ref();

			if (requestBody.getCompany_ref().equals(companyRef)) {

				Company company = compRef.getCompany();
				ArrayList<User> list = compRef.getUser();
				ArrayList<Distributor> distlist = compRef.getDistributor();

				ArrayList<User> user = allCompanyDetails.getUser();
				ArrayList<Distributor> distributor = allCompanyDetails.getDistributor();

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
					user.get(0).setRole("DISTRIBUTOR");
					user.get(0).setUser_status(true);
					user.get(0).setCreated_on(date);
					list.addAll(user);

					distributor.get(0).setCompany_ref(requestBody.getCompany_ref());
					distributor.get(0).setDistributor_name(requestBody.getUser_name());

					if (distlist != null) {
						distlist.addAll(distributor);
						String id = findByCompany_ref.getId();
						logger.info("Id is :" + id);
						allCompanyDetails.setId(id);
						allCompanyDetails.setCompany(company);
						allCompanyDetails.setUser(list);
						allCompanyDetails.setDistributor(distlist);
						regCompdao.save(allCompanyDetails);
					} else {

						String id = findByCompany_ref.getId();
						logger.info("Id is :" + id);
						allCompanyDetails.setId(id);
						allCompanyDetails.setCompany(company);
						allCompanyDetails.setUser(list);
						allCompanyDetails.setDistributor(distributor);
						regCompdao.save(allCompanyDetails);
					}

					logger.info("Distributor added successfully!");
					response.setData(user);
					response.setMessage(DISTRIBUTOR_SUCCESS);
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

}
