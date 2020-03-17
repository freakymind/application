package com.dsc.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.dsc.model.RegisterCompany;

@Repository
public interface RegisterCompnayDao extends MongoRepository<RegisterCompany, String> {

	@Query(value = "{ 'company': { $elemMatch: { 'email' : ?0 } }}")
	RegisterCompany findByCompanyEmail(String email);

	
	@Query(value = "{ 'company.company_id' : ?0 }")
	RegisterCompany findByCompanycompany_ref(String company_ref);
	
	
	@Query(value = "{ 'company.comp_admin_id' : ?0 }")
	RegisterCompany findByCompanycomp_admin_id(String comp_admin_id);
	
//	@Query(value = "{'id' : ?0 }", fields = "{ 'user' : 1 }")
//	@Query(value = "{'id':?0},{$push:{'user':?1}}}")
//	RegisterCompany findOneIncludeOnlyUser(String id, RegisterCompany registerCompany);
	
	@Query(value = "{ 'product.product_id' : ?0 }")
	RegisterCompany findByProductId(String product_id);

}
