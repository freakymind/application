package com.dsc.serviceimpl;

import static com.dsc.constants.CompanyConstants.PRODUCT_REF_ID;
import static com.dsc.constants.CompanyConstants.FAIL;
import static com.dsc.constants.CompanyConstants.NO_RECORDS;
import static com.dsc.constants.CompanyConstants.PRODUCT_EXISTS;
import static com.dsc.constants.CompanyConstants.PRODUCT_SUCCESS;
import static com.dsc.constants.CompanyConstants.PRODUCT_UPDATED_SUCCESS;
import static com.dsc.constants.CompanyConstants.SUCCESS;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dsc.dao.RegisterCompnayDao;
import com.dsc.handler.RegisterCompanyHandler;
import com.dsc.mapper.CompanyMapper;
import com.dsc.model.RegisterCompany;
import com.dsc.model.RegisterCompany.Company;
import com.dsc.model.RegisterCompany.Distributor;
import com.dsc.model.RegisterCompany.Product;
import com.dsc.model.RegisterCompany.User;
import com.dsc.model.User.UserDetails;
import com.dsc.response.UserResponse;
import com.dsc.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private RegisterCompnayDao regCompdao;
	UserResponse response = new UserResponse();

	private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
	private RegisterCompany findByProductId;
	private ArrayList<Product> product;
	private String product_id;

	@Override
	public UserResponse addProduct(RegisterCompanyHandler requestBody) throws Exception {

		logger.debug("Incoming request : " + requestBody);
		String generatedCompanyRef = PRODUCT_REF_ID;

		RegisterCompany allCompanyDetails = CompanyMapper.mapAllCompanyDetails(requestBody);
		List<RegisterCompany> findAll = regCompdao.findAll();

		for (RegisterCompany compRef : findAll) {
			String companyRef = compRef.getCompany().getCompany_ref();

			if (requestBody.getCompany_ref().equals(companyRef)) {
				Company company = compRef.getCompany();
				ArrayList<User> userlist = compRef.getUserid();
				ArrayList<Distributor> distlist = compRef.getDistributor();
				ArrayList<Product> prodlist = compRef.getProduct();

				ArrayList<User> user = allCompanyDetails.getUserid();
				ArrayList<Distributor> distributor = allCompanyDetails.getDistributor();
				ArrayList<Product> product = allCompanyDetails.getProduct();

				Date date = new Date();
				RegisterCompany details = null;
				RegisterCompany findByCompany_ref = regCompdao.findByCompanycompany_ref(requestBody.getCompany_ref());

				int count = 0;
				for (int i = 0; i < prodlist.size(); i++) {
					String pro_name = prodlist.get(i).getProduct_name();
					if (requestBody.getProduct_name().equals(pro_name)) {
						count++;
					}
				}
				System.out.println(count);
				if (count <= 0) {
					if (details == null) {
						product.get(0).setCreated_on(date);
						product.get(0).setUpdated_on(date);
						generatedCompanyRef += generatedRefIds();
						String generatedProductId = generatedRefIds();
						product.get(0).setProduct_id(generatedCompanyRef);
						product.get(0).setProduct_name(requestBody.getProduct_name());
						product.get(0).setProduct_brand(requestBody.getProduct_brand());
						product.get(0).setProduct_dimensions(requestBody.getProduct_dimensions());
						product.get(0).setProduct_model(requestBody.getProduct_model());
						product.get(0).setProduct_weight(requestBody.getProduct_weight());
						product.get(0).setBatch(requestBody.getBatch());
						product.get(0).setBatch_size(requestBody.getBatch_size());
						product.get(0).setCountry_code(requestBody.getCountry_code());

						if (prodlist != null) {
							prodlist.addAll(product);
							String id = findByCompany_ref.getId();
							logger.info("Id is :" + id);
							allCompanyDetails.setId(id);
							allCompanyDetails.setCompany(company);
							allCompanyDetails.setUserid(userlist);
							allCompanyDetails.setDistributor(distlist);
							allCompanyDetails.setProduct(prodlist);
							regCompdao.save(allCompanyDetails);

						} else {

							String id = findByCompany_ref.getId();
							logger.info("Id is :" + id);
							allCompanyDetails.setId(id);
							allCompanyDetails.setCompany(company);
							allCompanyDetails.setUserid(userlist);
							allCompanyDetails.setDistributor(distlist);
							allCompanyDetails.setProduct(product);
							regCompdao.save(allCompanyDetails);
						}
					}
					logger.info("Product added successfully!");
					response.setData(product);
					response.setMessage(PRODUCT_SUCCESS);
					response.setStatus(SUCCESS);
					return response;

				}
			}
		}

		logger.error("Failed to process request");
		response.setStatus(FAIL);
		response.setMessage(PRODUCT_EXISTS);
		return response;

	}

	public String generatedRefIds() {
		Random ran = new Random();
		String b = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String sc = "0123456789";
		String random = "";
		for (int j = 0; j < 2; j++) {
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

	@Override
	public UserResponse updateProduct(RegisterCompanyHandler requestBody) throws Exception {
		logger.debug("Incoming update request : " + requestBody);
		RegisterCompany allCompanyDetails = CompanyMapper.mapAllCompanyDetails(requestBody);
		Date date = new Date();
		RegisterCompany findByCompanyRef = regCompdao.findByCompanycompany_ref(requestBody.getCompany_ref());
		System.out.println("company ref Id::" + findByCompanyRef);

		Company company = findByCompanyRef.getCompany();
		ArrayList<User> compUserIdList = findByCompanyRef.getUserid();
		ArrayList<Distributor> DistributorList = findByCompanyRef.getDistributor();
		ArrayList<Product> productList = findByCompanyRef.getProduct();

		int i = 0;
		for (Product productData : productList) {
			String prodId = productData.getProduct_id();
			Date created_on = productData.getCreated_on();
			Date updated_on = productData.getUpdated_on();
			String companyRef = productData.getCompany_ref();

			if ((prodId.equals(requestBody.getProduct_id())) && (companyRef.equals(requestBody.getCompany_ref()))) {
				productData.setCreated_on(created_on);
				productData.setUpdated_on(updated_on);
				productData.setProduct_id(prodId);
				productData.setProduct_name(requestBody.getProduct_name());
				productData.setProduct_brand(requestBody.getProduct_brand());
				productData.setProduct_dimensions(requestBody.getProduct_dimensions());
				productData.setCountry_code(requestBody.getCountry_code());
				productData.setProduct_model(requestBody.getProduct_model());
				productData.setProduct_weight(requestBody.getProduct_weight());
				productData.setBatch(requestBody.getBatch());
				productData.setBatch_size(requestBody.getBatch_size());
				productList.set(i, productData);
				String id = findByCompanyRef.getId();
				logger.info("Id is :" + id);
				allCompanyDetails.setId(id);
				allCompanyDetails.setCompany(company);
				allCompanyDetails.setUserid(compUserIdList);
				allCompanyDetails.setProduct(productList);
				allCompanyDetails.setDistributor(DistributorList);

				regCompdao.save(allCompanyDetails);
				logger.info("product details updated successfully!");
				response.setData(productData);
				response.setMessage(PRODUCT_UPDATED_SUCCESS);
				response.setStatus(SUCCESS);
				return response;
			}
			i++;

		}
		logger.error("Failed to process update request");
		response.setStatus(FAIL);
		response.setMessage(PRODUCT_EXISTS);
		return response;
	}

	public UserResponse getProduct(RegisterCompanyHandler requestBody) throws Exception {
		com.dsc.model.RegisterCompany findByProductId = regCompdao.findByProductId(requestBody.getProduct_id());

		ArrayList<Product> productList = findByProductId.getProduct();

		for (Product productDetails : productList) {
			String productId = productDetails.getProduct_id();

			if (productId.equals(requestBody.getProduct_id())) {
				response.setData(productDetails);
				response.setStatus(SUCCESS);
				response.setMessage("Product Details");
				return response;

			}
		}

		response.setMessage(NO_RECORDS);
		response.setStatus(FAIL);
		return response;

	}
}